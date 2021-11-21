package bhutan.eledger.application.port.out.epayment.reconciliation;

public interface BankStatementFileParserPort {

    //todo for Aleksandr define return domain object
    void getStatements(String fileId);
}
