package bhutan.eledger.common.userdetails;

import lombok.Data;

@Data
class UserDetailsImpl implements UserDetails {
    private final String username;
}
