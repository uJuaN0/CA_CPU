package org.ac;

/**
 * Class that models a CPU general-purpose register, a fast access memory used to store data to be supplied
 * to instruction execution units, such as the ALU.
 */
public class Register {

    /**
     * Size of the general-purpose registers, by default.
     */
    public static final int GP_REGISTER_SIZE = 8;

    /**
     * The bit representation of the register's contents
     */
    final boolean[] bits;

    /**
     * Create a register with a given size
     */
    public Register(int size) {
        this.bits = new boolean[size];
    }

    /**
     * Create a register with the CPU's default register size
     */
    public Register() {
        this(GP_REGISTER_SIZE);
    }

    /**
     *  Sets the bit at position index to value.
     */
    public void setBit(int index, boolean value) {
        bits[index] = value;
    }

    /**
     * Returns the value of the bit at position index.
     */
    public boolean getBit(int index) {
        return bits[index];
    }

    /**
     * Stores an integer using an N-bit two’s complement representation.
     */
    // TODO
    public void set(int value) {
        // In case value>=0 it will only convert to binary
        if (value>=0){
            for (int i=0;i<bits.length;i++){
                setBit(i,value % 2 != 0);
                value /= 2;
            }
        // In case value<0 it will make the conversion and the 2's complement
        } else {
            value = Math.abs(value);
            for (int i=0;i<bits.length;i++) {
                setBit(i,value % 2 != 0);
                value /= 2;
            }
            // 1's complement first
            for (int i=0;i<bits.length;i++) {
                setBit(i,!getBit(i));
            }
            int pos = 0;
            boolean carry = true;
            // 2's complement
            while (pos < bits.length && carry) {
                if (getBit(pos)) {
                    setBit(pos,false);
                    pos++;
                } else {
                    setBit(pos,true);
                    carry = false;
                }
            }
        }
    }

    /**
     * Interprets the register contents as an unsigned integer.
     */
    public int getUnsignedInt() {
        int result = 0;
        for (int i=0;i<bits.length;i++){
            if (getBit(i)){
                result += (int) Math.pow(2,i);
            }
        }
        return result;
    }


    /**
     *  Interprets the register contents as a signed integer in two’s complement.
     */
    public int getInt() {
        int result = getUnsignedInt();
        if (getBit(bits.length-1)){
            result -= 256;
        }
        return result;
    }

    /**
     * Returns a string containing the register contents organized in groups of 4 bits separated by spaces (e.g., 1111 1110).
     */
    @Override
    public String toString() {
        String result = "";
        // Runs the bits array from the MSB
        for (int i = bits.length-1;i>=0;i--){
            // Makes the space on the "0000 0000" representation
            if (i==3){
                result += (" ");
            }
            if (getBit(i)){
                result += ("1");
            } else {
                result += ("0");
            }
        }
        return result;
    }
}
