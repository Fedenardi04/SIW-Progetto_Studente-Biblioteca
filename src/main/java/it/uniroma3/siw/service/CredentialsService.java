package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Role;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
@Transactional(readOnly = true)
public class CredentialsService {

    private CredentialsRepository credentialsRepository;
    private PasswordEncoder passwordEncoder;

    public CredentialsService(CredentialsRepository credentialsRepository,
                              PasswordEncoder passwordEncoder) {
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Role.DEFAULT);
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        return credentialsRepository.save(credentials);
    }

	public Optional<Credentials> findByUsername(String username) {
		return this.credentialsRepository.findByUsername(username);
	}
}