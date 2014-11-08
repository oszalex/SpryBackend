package rest.security;

/**
 * Created by chris on 08.11.14.
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import rest.exception.UsernameAlreadyInUseException;
import rest.service.UserRepository;
import rest.domain.User;

@Repository
public class InMemoryAccountRepository implements UserRepository {

    private List<User> users;

    public InMemoryAccountRepository() {
        users = new ArrayList<User>();
        init();
    }

    private void init() {
        User account = new User();
        account.setPhoneNumber(4369911602033L);
        account.setPassword("1234");
        users.add(account);
    }

    @Override
    public User findByPhoneNumber(Long phoneNumber) {
        if(phoneNumber == null) return null;

        User result = null;
        for (User account : users) {
            if (account.getPhoneNumber() == phoneNumber) {
                result = account;
                break;
            }
        }
        return result;
    }

    @Override
    public void createAccount(User account) throws UsernameAlreadyInUseException {
        users.add(account);
    }
}