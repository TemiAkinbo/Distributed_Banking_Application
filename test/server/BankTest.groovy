package server

import java.text.SimpleDateFormat

class BankTest extends GroovyTestCase {

    void testInquiry() {
        def bank = new Bank()
        def sessionID = bank.login("User1", "pass1")
        def accountNum = 5628291

        def expectedBalance = 1500
        def actualBalance = bank.inquiry(accountNum, sessionID)

        println("Returned Balance = " + actualBalance)

        assert actualBalance == expectedBalance
    }

    void testWithdraw() {
        def bank = new Bank()
        def sessionID = bank.login("User1", "pass1")
        def accountNum = 5628291

        def initialBalance = bank.inquiry(accountNum, sessionID)
        def withdrawalAmount = 500
        def expectedFinalBalance = initialBalance - withdrawalAmount

        def actualFinalBalance = bank.withdraw(accountNum, withdrawalAmount, sessionID)

        assert actualFinalBalance == expectedFinalBalance
    }

    void testDeposit() {
        def bank = new Bank()
        def sessionID = bank.login("User1", "pass1")
        def accountNum = 5628291

        def initialBalance = bank.inquiry(accountNum, sessionID)
        def depositAmount = 500
        def expectedFinalBalance = initialBalance + depositAmount

        def actualFinalBalance = bank.deposit(accountNum, depositAmount, sessionID)

        assert actualFinalBalance == expectedFinalBalance
    }

    void testStatement() {
        def bank = new Bank()
        def sessionID = bank.login("User1", "pass1")
        def accountNum = 5628291
        def dateFormatter = new SimpleDateFormat("dd/MM/yyyy")
        def outputDateFormatter = new SimpleDateFormat("yyyy-MM-dd")
        def today = outputDateFormatter.format(new Date(System.currentTimeMillis()))

        def startDateString = "01/01/2019"
        def endDateString = "01/03/2019"

        def startDate = dateFormatter.parse(startDateString)
        def endDate = dateFormatter.parse(endDateString)

        bank.withdraw(accountNum, 200, sessionID)
        bank.deposit(accountNum, 150, sessionID)

        def expectedResult = "*** Account Statement **** \n\n" +
                "Account Owner: User1\n" +
                "Account Number: 5628291\n" +
                "Start Date : 2019-01-01\n" +
                "End Date : 2019-03-01\n\n" +
                "2019-01-01 | OPENING BALANCE : 1500.0\n\n" +
                today + " - Withdraw : 200.0 | balance : 1300.0\n" +
                today + " - Deposit : 150.0 | balance : 1450.0\n\n" +
                "2019-03-01 | CLOSING BALANCE : 1450.0"

        def actualResult = bank.getStatement(accountNum, startDate, endDate, sessionID)

        assert actualResult == expectedResult
    }
}
