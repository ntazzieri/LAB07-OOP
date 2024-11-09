package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int AMOUNT = 100;
    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        final int transactionBeforeDeposit = bankAccount.getTransactionsCount();
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);
        assertEquals(transactionBeforeDeposit + 1, bankAccount.getTransactionsCount());
        assertEquals(AMOUNT, bankAccount.getBalance());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(AMOUNT - (StrictBankAccount.MANAGEMENT_FEE + (transactionBeforeDeposit + 1) * StrictBankAccount.TRANSACTION_FEE), bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        final double amountBeforeWithdraw = bankAccount.getBalance();
        final int transactionsBeforeWithdraw = bankAccount.getTransactionsCount();
        try {
            bankAccount.withdraw(mRossi.getUserID(), -AMOUNT);
            fail("Withdrawing a negative amount is possible");
        } catch (IllegalArgumentException e) {
            assertEquals(amountBeforeWithdraw, bankAccount.getBalance());
            assertEquals(transactionsBeforeWithdraw, bankAccount.getTransactionsCount());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        final double amountBeforeWithdraw = bankAccount.getBalance();
        final int transactionsBeforeWithdraw = bankAccount.getTransactionsCount();
        try {
            bankAccount.withdraw(mRossi.getUserID(), amountBeforeWithdraw - (2 * amountBeforeWithdraw));
            fail("Withdrawing more money than it is in the account is possible");
        } catch (IllegalArgumentException e) {
            assertEquals(amountBeforeWithdraw, bankAccount.getBalance());
            assertEquals(transactionsBeforeWithdraw, bankAccount.getTransactionsCount());
        }
    }
}
