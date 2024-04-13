package br.com.ifsp.tickets.infra.user;

import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.Email;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.infra.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.user.persistence.UserRepository;
import br.com.ifsp.tickets.infra.user.persistence.spec.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class UserGateway implements IUserGateway {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return this.userRepository.save(UserJpaEntity.from(user)).toAggregate();
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return this.userRepository.findByEmail(email.getValue()).map(UserJpaEntity::toAggregate);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username).map(UserJpaEntity::toAggregate);
    }

    @Override
    public Optional<User> findByCPF(CPF cpf) {
        return this.userRepository.findByCpf(cpf.getValue()).map(UserJpaEntity::toAggregate);
    }

    @Override
    public Optional<User> findById(UserID id) {
        return this.userRepository.findById(id.getValue()).map(UserJpaEntity::toAggregate);
    }

    @Override
    public User update(User user) {
        return this.userRepository.save(UserJpaEntity.from(user)).toAggregate();
    }

    @Override
    public Pagination<User> findAll(AdvancedSearchQuery sq) {
        final UserSpecificationBuilder builder = new UserSpecificationBuilder();
        sq.filters().forEach(builder::with);
        final Specification<UserJpaEntity> spec = builder.build();

        final PageRequest request = PageRequest.of(
                sq.page(),
                sq.perPage(),
                Sort.by(Sort.Direction.fromString(sq.direction()), sq.sort())
        );

        final Page<User> userPage = this.userRepository.findAll(spec, request).map(UserJpaEntity::toAggregate);

        return Pagination.of(
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getContent()
        );
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(UserJpaEntity.from(user));
    }

    @Override
    public boolean existsById(UserID id) {
        return this.userRepository.existsById(id.getValue());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return this.userRepository.existsByEmail(email.getValue());
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByPhoneNumber(PhoneNumber phoneNumber) {
        return this.userRepository.existsByPhoneNumber(phoneNumber.getValue());
    }

    @Override
    public boolean existsByCPF(CPF cpf) {
        return this.userRepository.existsByCpf(cpf.getValue());
    }
}
