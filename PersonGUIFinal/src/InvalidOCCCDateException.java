// Makayla Wood
// Advanced Java
// Spring 2026
// Exception class for OCCCDate; based on Clay Waldroup's demo code

public class InvalidOCCCDateException extends IllegalArgumentException
{
    String msg; 
    
    public InvalidOCCCDateException()
    {
        super();
        this.msg = "Date is invalid";
    }
    
    public InvalidOCCCDateException(String msg)
    {
        super(msg);
        this.msg = new String(msg);
    }
    
    public String getMessage()
    {
        return msg;
    }
    
    @Override
    public String toString()
    {
        return "InvalidOCCCDateException: " + msg;
    }
}
