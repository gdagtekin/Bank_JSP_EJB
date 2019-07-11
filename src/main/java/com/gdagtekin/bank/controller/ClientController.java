package com.gdagtekin.bank.controller;

import com.gdagtekin.bank.model.Account;
import com.gdagtekin.bank.model.Client;
import com.gdagtekin.bank.model.Operation;
import com.gdagtekin.bank.service.AccountService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.gdagtekin.bank.service.ClientService;
import com.gdagtekin.bank.service.InterestService;
import com.gdagtekin.bank.service.OperationService;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Gökhan DAĞTEKİN
 *
 * 2019 - Bialystok University of Technology
 */
@Named
@ViewScoped
public class ClientController implements Serializable {

    @EJB
    private ClientService clientService;
    @EJB
    private AccountService accountService;
    @EJB
    private OperationService operationService;

    private List<Client> clients;
    private Client selectedClient;
    private Account selectedAccount;
    private List<Operation> operations;

    private Client sourceClient;
    private Account sourceAccount;

    private Integer amount = 0;
    private String message;

    @PostConstruct
    private void init() {
        clients = clientService.findAll();

    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Account.Type[] getAccountTypes() {
        return Account.Type.values();
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client editedClient) {
        this.selectedClient = editedClient;
        onCancelAccount();
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
        operations = operationService.findAll();

    }

    public void setSelectedAccountOperation(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public void onAddClient() {
        selectedClient = new Client();
    }

    public void onSaveClient() {
        if (selectedClient.getId() == null) {
            clientService.create(selectedClient);
            clients.add(selectedClient);
        } else {
            clientService.update(selectedClient);
        }
        selectedClient = null;
    }

    public void onCancelClient() {
        selectedClient = null;
    }

    public void onRemoveClient() {
        clientService.delete(selectedClient.getId());
        clients.remove(selectedClient);
        selectedClient = null;
    }

    public void onAddAccount() {
        selectedAccount = new Account();
    }

    public void onSaveAccount() {
        if (selectedAccount.getId() == null) {
            selectedClient.add(selectedAccount);
            accountService.create(selectedAccount);
        } else {
            accountService.update(selectedAccount);
        }
    }

    public void onCancelAccount() {
        selectedAccount = null;
    }

    public void onRemoveAccount(Account a) {
        a.getClient().getAccounts().remove(a);
        clientService.update(a.getClient());
    }

    public boolean statusAccount(Account a) {
        return a.isStatus();
    }

    public boolean statusAccountAndTransfer(Account a) {
        if (a.isStatus() && sourceAccount != null) {
            return true;
        } else if (a.isStatus() && sourceAccount == null) {
            return true;
        } else if (!a.isStatus() && sourceAccount != null) {
            return true;
        } else if (!a.isStatus() && sourceAccount == null) {
            return false;
        } else {
            return a.isStatus();
        }
    }

    public void onDisableAccount(Account a) {
        selectedAccount = a;
        selectedAccount.setStatus(true);
        accountService.update(selectedAccount);
    }

    public void onActivateAccount(Account a) {
        a.setStatus(false);
        accountService.update(a);
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Client getSourceClient() {
        return sourceClient;
    }

    public void setSourceClient(Client sourceClient) {
        this.sourceClient = sourceClient;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Operation operationSetup(Operation.Type type, Account source, Account destination) {
        Operation operation = new Operation();
        operation.setType(type);
        operation.setAmount(BigDecimal.valueOf(amount));

        /*DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        operation.setDate(dateFormat.format(date));*/
        operation.setDate(Calendar.getInstance().getTime());
        operation.setSource(source);
        operation.setDestination(destination);

        return operation;
    }

    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
    }

    public void onWithdraw() {
        Operation operation = operationSetup(Operation.Type.WITHDRAW, selectedAccount, selectedAccount);
        if (selectedAccount.getBalance().compareTo(BigDecimal.valueOf(amount)) >= 0 && amount > 0) {
            message = "Withdraw $" + String.valueOf(amount);
            selectedAccount.setBalance(selectedAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
            selectedAccount.add(operation);
            operationService.create(operation);
            accountService.update(selectedAccount);
            saveMessage();
        } else {
            message = "Not enough balance";
            saveMessage();
        }
        amount = 0;
    }

    public void onDeposit() {
        Operation operation = operationSetup(Operation.Type.DEPOSIT, selectedAccount, selectedAccount);
        if (amount > 0) {
            message = "Deposit $" + String.valueOf(amount);
            selectedAccount.setBalance(selectedAccount.getBalance().add(BigDecimal.valueOf(amount)));
            selectedAccount.add(operation);
            operationService.create(operation);
            accountService.update(selectedAccount);
            saveMessage();
        } else {
            message = "Do not enter a negative value";
            saveMessage();
        }
        amount = 0;
    }

    public boolean renderTranferThisAccountButton(Account a) {
        if (sourceAccount != null && !sourceAccount.equals(a)) {
            return true;
        } else {
            return false;
        }
    }

    public void onBeforeTransfer(Account a) {
        sourceClient = selectedClient;
        sourceAccount = a;
        onCancelAccount();
        onCancelClient();
        message = "Select destination client and account";
        saveMessage();
    }

    public void onTransfer() {
        Operation operation = operationSetup(Operation.Type.TRANSFER, sourceAccount, selectedAccount);
        if (amount > 0) {
            if (sourceAccount.getBalance().compareTo(BigDecimal.valueOf(amount)) >= 0) {
                message = "Account: " + String.valueOf(sourceAccount.getId()) + " transfer $" + String.valueOf(amount) + " to " + String.valueOf(selectedAccount.getId());
                saveMessage();
                sourceAccount.setBalance(sourceAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
                selectedAccount.setBalance(selectedAccount.getBalance().add(BigDecimal.valueOf(amount)));
                selectedAccount.add(operation);
                sourceAccount.add(operation);
                operationService.create(operation);
                accountService.update(sourceAccount);
                accountService.update(selectedAccount);

            } else {
                message = "Not enough balance";
                saveMessage();
            }

        } else {
            message = "Do not enter a negative value";
            saveMessage();
        }
        amount = 0;
        selectedClient = null;
        selectedAccount = null;
        sourceAccount = null;
        sourceClient = null;
    }

    public void onCancelTransfer() {
        sourceAccount = null;
        sourceClient = null;
        onCancelAccount();
        onCancelClient();
    }

    public void onInterest() {

        if (amount > 0) {
            message = "Interest $" + String.valueOf(amount);
            //INTEREST ACCOUNT CREATED
            Account account = new Account();
            account.setClient(selectedClient);
            account.setNotes("Interest Account");
            account.setType(Account.Type.INTEREST);
            account.setBalance(BigDecimal.valueOf(amount));
            selectedClient.add(account);
            accountService.create(account);

            Operation operation = operationSetup(Operation.Type.INTEREST, selectedAccount, account);

            selectedAccount.setBalance(selectedAccount.getBalance().subtract(BigDecimal.valueOf(amount)));
            selectedAccount.add(operation);
            account.add(operation);

            operationService.create(operation);
            accountService.update(selectedAccount);
            saveMessage();
        } else {
            message = "Do not enter a negative value";
            saveMessage();
        }
        amount = 0;
    }

}
