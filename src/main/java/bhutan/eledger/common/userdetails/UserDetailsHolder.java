package bhutan.eledger.common.userdetails;

public interface UserDetailsHolder {
    void set(UserDetails userDetails);

    UserDetails get();

    void remove();
}
