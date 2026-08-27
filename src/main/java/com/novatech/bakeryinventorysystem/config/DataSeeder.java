package com.novatech.bakeryinventorysystem.config;

import com.novatech.bakeryinventorysystem.model.Role;
import com.novatech.bakeryinventorysystem.model.User;
import com.novatech.bakeryinventorysystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setFullName("Administrador");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("panaderia2026"));
            admin.setRole(Role.JEFE);
            userRepository.save(admin);
            System.out.println(">>> Usuario admin creado con éxito");}
        if (userRepository.findByUsername("empleado").isEmpty()) {
            User empleado = new User();
            empleado.setFullName("Empleado");
            empleado.setUsername("empleado");
            empleado.setPassword(passwordEncoder.encode("empleado2026"));
            empleado.setRole(Role.EMPLEADO);
            userRepository.save(empleado);
            System.out.println(">>> Usuario empleado creado con éxito");
        }}
}