package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Tony", "Stark", "stark@mindhub.com");

			clientRepository.save(client1);
			clientRepository.save(client2);

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



		};
	}

}
