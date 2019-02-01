import { Command } from './command';

export class RegisterCommand implements Command {
    public command = "register";
    
    constructor( public username : string, 
                 public password : string ) {};
}

export class LoginCommand implements Command {
    public command = "login";
    
    constructor( public username : string, 
                 public password : string ){};
}

export class LoginResult implements Command {
    public command = "loginResult";
    
    constructor( public authToken : string, 
                 public error : string,
                 public errorCode : number ) {};
}