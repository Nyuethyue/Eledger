package bhutan.eledger.common.userdetails;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
class UserDetailsHolderService implements UserDetailsHolder {
    private final ThreadLocal<UserDetails> userDetailsStorage = new ThreadLocal<>();

    @Override
    public void set(UserDetails userDetails) {
        if (userDetailsStorage.get() != null) {
            log.warn("Value in 'userDetailsStorage' exists.");
        }

        userDetailsStorage.set(userDetails);
    }

    @Override
    public UserDetails get() {
        return userDetailsStorage.get();
    }

    @Override
    public void remove() {
        userDetailsStorage.remove();
    }
}
