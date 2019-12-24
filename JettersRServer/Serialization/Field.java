package JettersRServer.Serialization;
/**
 * Write a description of class Field here.
 *
 * @author: Luke Sullivan
 * @10/11/19
 */

public class Field
{
    public static final byte CONTAINER_TYPE = ContainerType.FIELD;//Field, array, object
    public short nameLength;
    public byte[] name;
    public byte type;
    public byte[] data;
    
    private Field()
    {
        
    }
    
    public static Field ByteField(String name, byte value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.BYTE;
       field.data = new byte[Type.getSize(Type.BYTE)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field ShortField(String name, short value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.SHORT;
       field.data = new byte[Type.getSize(Type.SHORT)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field CharField(String name, char value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.CHAR;
       field.data = new byte[Type.getSize(Type.CHAR)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field IntField(String name, int value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.INT;
       field.data = new byte[Type.getSize(Type.INT)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field LongField(String name, long value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.LONG;
       field.data = new byte[Type.getSize(Type.LONG)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field FloatField(String name, float value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.FLOAT;
       field.data = new byte[Type.getSize(Type.FLOAT)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field DoubleField(String name, double value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.DOUBLE;
       field.data = new byte[Type.getSize(Type.DOUBLE)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    
    public static Field BooleanField(String name, boolean value)
    {
       Field field = new Field();
       field.setName(name);
       field.type = Type.BOOLEAN;
       field.data = new byte[Type.getSize(Type.BOOLEAN)];
       SerializationWriter.writeBytes(field.data, 0, value);
       return field;
    }
    //
    //
    //
    public void setName(String name)
    {
        assert(name.length() < Short.MAX_VALUE);
        
        nameLength = (short)name.length();
        this.name = name.getBytes();
    }
    
    public int getBytes(byte[] dest, int pointer)
    {
        //dest[pointer++] = containerType;
        pointer = SerializationWriter.writeBytes(dest, pointer, CONTAINER_TYPE);
        pointer = SerializationWriter.writeBytes(dest, pointer, nameLength);
        pointer = SerializationWriter.writeBytes(dest, pointer, name);
        pointer = SerializationWriter.writeBytes(dest, pointer, type);
        pointer = SerializationWriter.writeBytes(dest, pointer, data);
        return pointer;
    }
    
    public int getSize()
    {
        assert(data.length == Type.getSize(type));
        return 1 + 2 + name.length + type + data.length;
    }
}
