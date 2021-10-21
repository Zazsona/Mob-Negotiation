package com.zazsona.mobnegotiation.model.exception;

public class InvalidParticipantsException extends Exception
{
    public InvalidParticipantsException()
    {
        super();
    }

    public InvalidParticipantsException(String message)
    {
        super(message);
    }

    public InvalidParticipantsException(Throwable cause)
    {
        super(cause);
    }

    public InvalidParticipantsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
