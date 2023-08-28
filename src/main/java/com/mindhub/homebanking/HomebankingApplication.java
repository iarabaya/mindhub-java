package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

			Loan loan1 = new Loan("hipotecario", 500000, Arrays.asList(12,24,36,48,60));
			Loan loan2 = new Loan("personal", 100000, Arrays.asList(6,12,24));
			Loan loan3 = new Loan("automotriz", 300000, Arrays.asList(6,12,24,36));

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("1234"));
			Client client2 = new Client("Tony", "Stark", "stark@mindhub.com", passwordEncoder.encode("1234"));

			clientRepository.save(client1);
			clientRepository.save(client2);

			//2 loans para Melba

			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000,12,client1,loan2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			client1.addLoan(clientLoan1);
			client1.addLoan(clientLoan2);

			loan1.addClient(clientLoan1);
			loan2.addClient(clientLoan2);

			//2 loans para Tony

			ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000,36,client2,loan3);

			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			client2.addLoan(clientLoan3);
			client2.addLoan(clientLoan4);

			loan2.addClient(clientLoan3);
			loan3.addClient(clientLoan4);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000.00);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00);
			Account account3 = new Account("VIN003", LocalDate.now().minusYears(3), 70500.00);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			Transaction tran1 = new Transaction(TransactionType.CREDITO, 1300.00, "cine", LocalDateTime.now());
			Transaction tran2 = new Transaction(TransactionType.CREDITO, 2500.00, "hamburguesa", LocalDateTime.now().plusDays(1));
			Transaction tran3 = new Transaction(TransactionType.DEBITO, -1400.00, "cafe", LocalDateTime.now().plusDays(2));
			Transaction tran4 = new Transaction(TransactionType.DEBITO, -8700.00, "cena", LocalDateTime.now().plusDays(3));
			Transaction tran5 = new Transaction(TransactionType.DEBITO, -1400.00, "cafe", LocalDateTime.now().plusDays(2));
			Transaction tran6 = new Transaction(TransactionType.DEBITO, -8700.00, "cena", LocalDateTime.now().plusDays(3));

			account1.addTransaction(tran1);
			account1.addTransaction(tran4);

			account2.addTransaction(tran2);
			account2.addTransaction(tran3);

			account3.addTransaction(tran5);
			account3.addTransaction(tran6);

			transactionRepository.save(tran1);
			transactionRepository.save(tran2);
			transactionRepository.save(tran3);
			transactionRepository.save(tran4);
			transactionRepository.save(tran5);
			transactionRepository.save(tran6);


			Card card1 = new Card(client1, CardType.DEBIT, CardColor.GOLD, "3325-6745-7876-4445", 990, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2 = new Card(client1, CardType.CREDIT, CardColor.TITANIUM, "2234-6745-5534-7888", 750, LocalDate.now(), LocalDate.now().plusYears(5));
			Card card3 = new Card(client2, CardType.CREDIT, CardColor.SILVER, "1236-8892-1256-7296", 665, LocalDate.now(), LocalDate.now().plusYears(5));

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		};
	}

}
