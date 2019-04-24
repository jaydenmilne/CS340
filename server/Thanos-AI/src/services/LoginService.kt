package services

import ICommandRouter
import LoginUserDTO
import IProxyServer
import commands.ILoginCommand
import commands.IRegisterCommand
import commands.LOGIN_RESULT
import commands.LoginResultCommand

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.LoginService Handles Login and Registration
 */
class LoginService(private val server: IProxyServer, private val cmdRouter: ICommandRouter) {
    private var user: LoginUserDTO? = null

    init {
        this.cmdRouter.registerCallback(LOGIN_RESULT) { handleLoginResult(it as LoginResultCommand) }
    }

    fun login(username: String, password: String){
        server.executeCommand(ILoginCommand(username, password))
    }

    fun register(username: String, password: String){
        server.executeCommand(IRegisterCommand(username, password))
    }

    fun handleLoginResult(loginResultCommand: LoginResultCommand){
        if(loginResultCommand.error != ""){
            println("Login Error ${loginResultCommand.error}")
            server.setAuthToken(null)
            user = null
            return
        }
        // TODO: Handle Re-entering a game?
        user = loginResultCommand.user
        server.setAuthToken(user!!.authToken)
        println("Login Successful: ${user!!.username}")
    }

    fun isLoggedIn():Boolean {
        return user != null
    }

    fun getUserId(): Int {
        return user?.userId ?: -1
    }
}