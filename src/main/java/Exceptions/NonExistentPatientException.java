package Exceptions;

public class NonExistentPatientException extends Exception{
    public NonExistentPatientException(String message){
        super(message);
    }
}
