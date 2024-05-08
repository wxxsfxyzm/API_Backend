package com.carlyu.apibackend.service.impl

import com.carlyu.apibackend.dao.IUserInfoDAO
import com.carlyu.apibackend.entity.User
import com.carlyu.apibackend.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val iUserInfoDAO: IUserInfoDAO
) : UserService {

    override fun findById(id: Long): User? = iUserInfoDAO.findById(id)


    override fun findByUsername(username: String): User? = iUserInfoDAO.findByUsername(username)


    override fun existsByUsername(username: String): Boolean = iUserInfoDAO.findByUsername(username) != null


    override fun save(user: User): User? = iUserInfoDAO.save(user)
}
