package com.econ.managify.interfaces;

import com.econ.managify.exceptions.AuthException;
import com.econ.managify.exceptions.IssueException;
import com.econ.managify.models.User;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws AuthException;
    User findUserByEmail(String email)  throws AuthException;
    User findUserById(Long userId)  throws AuthException, IssueException;
    User updateUsersProjectSize(User user, int number)  throws Exception;


}
