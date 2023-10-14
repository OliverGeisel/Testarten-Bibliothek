package de.olivergeisel.teddjbrary.user.staff;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface AngestellterRepository extends CrudRepository<Angestellter, UUID> {
	Optional<Angestellter> findByUserAccount(UserAccount userAccount);
}
