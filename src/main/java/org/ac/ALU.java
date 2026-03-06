package org.ac;

import static org.ac.Register.GP_REGISTER_SIZE;

/**
 * The class the implements the Arithmetic and Login Unit (ALU)
 */
public class ALU {

    /**
     *  Flags register.
     *  Bit 0 (Negative): Set if the result of the last operation executed by the ALU is negative, that is,
     *                    that MSB of the register holding the result is 1.
     *  Bit 1 (Zero): Set if the result of the last operation was exactly zero.
     *  Bit 2 (Carry): Set if a carry occurred in the last operation executed by the ALU.
     *  Bit 3 (Overflow): et if an arithmetic overflow occurred in the last operation executed by the ALU.
     */
    final Register NZCV = new Register(GP_REGISTER_SIZE);

    public void flags(Register in){
        // Negative value flag
        if (in.getBit(7)){
            NZCV.setBit(0,true);
        } else {
            NZCV.setBit(0,false);
        }
        // Zero flag
        if (in.getInt()==0){
            NZCV.setBit(1,true);
        } else {
            NZCV.setBit(1,false);
        }
    }

    /**
     * Stores in register out the result of the bitwise inversion of register in (one’s complement).
     * Assume that both registers have the same size.
     */
    public void not(Register out, Register in) {
        for (int i=0; i<GP_REGISTER_SIZE;i++){
            out.setBit(i,!in.getBit(i));
            flags(out);
        }
    }

    /**
     * Stores in register out the result of the bitwise logical AND between rin1 and rin2.
     * Assume that all registers have the same size.
     */
    public void and(Register out, Register in1, Register in2) {
        for (int i=0; i<GP_REGISTER_SIZE;i++){
            if (in1.getBit(i) && in2.getBit(i)){
                out.setBit(i,true);
            }
        }
        flags(out);
    }

    /**
     * Stores in register out the result of the bitwise logical OR between rin1 and rin2.
     * Assume that all registers have the same size.
     */
    public void or(Register out, Register in1, Register in2) {
        for (int i=0; i<GP_REGISTER_SIZE;i++){
            if (in1.getBit(i) || in2.getBit(i)){
                out.setBit(i,true);
            }
        }
        flags(out);
    }

    /**
     * Stores in register out the result of the bitwise logical XOR between rin1 and rin2.
     * Assume that all registers have the same size.
     */
    public void xor(Register out, Register in1, Register in2) {
        for (int i=0; i<GP_REGISTER_SIZE;i++){
            if (in1.getBit(i) || in2.getBit(i) && in1.getBit(i) != in2.getBit(i)){
                out.setBit(i,true);
            }
        }
        flags(out);
    }

    /**
     * Stores in register out the result of a logical shift left operation on register in.
     * Assume that both registers have the same size.
     */
    public void lsl(Register out, Register in, int n) {
        boolean oldMSB = in.getBit(GP_REGISTER_SIZE - 1);

        for (int i = 0; i < GP_REGISTER_SIZE; i++) {
            out.setBit(i, in.getBit(i));
        }

        boolean carry = false;

        for (int step = 0; step < n; step++) {
            // carry do passo atual (bit que vai sair do MSB)
            carry = out.getBit(GP_REGISTER_SIZE - 1);

            // shift left de 1
            for (int i = GP_REGISTER_SIZE - 1; i > 0; i--) {
                out.setBit(i, out.getBit(i - 1));
            }
            out.setBit(0, false);
        }

        // flags
        NZCV.setBit(2, carry); // Carry do último shift
        boolean overflow = oldMSB ^ out.getBit(GP_REGISTER_SIZE - 1);
        NZCV.setBit(3, overflow);

        flags(out); // N e Z do resultado final
    }

    /**
     * Stores in register out the result of the arithmetic negation of in (two’s complement).
     * Assume that both registers have the same size.
     */
    public void neg(Register out, Register in) {
        // TODO
    }

    /**
     * Implements a Ripple Carry Adder (manually handling carries) to compute out = in1 + in2.
     * Assume that all registers have the same size.
     */
    public void add(Register out, Register in1, Register in2) {
        boolean carry = false;
        for (int i = 0; i<GP_REGISTER_SIZE;i++){
            if (in1.getBit(i) != (in2.getBit(i) && !carry)){
                out.setBit(i,true);
                carry = false;
            } else if (in1.getBit(i) == in2.getBit(i) && in1.getBit(i)){
                carry = true;
                out.setBit(i,false);
            } else {
                carry = false;
                out.setBit(i,false);
            }
        }
    }

    /**
     * Return the value of the Negative flag
     */
    public boolean negativeFlag() {
        return NZCV.getBit(0);
    }

    /**
     * Return the value of the Zero flag
     */
    public boolean zeroFlag() {
        return NZCV.getBit(1);
    }

    /**
     * Return the value of the Carry flag
     */
    public boolean carryFlag() {
        return NZCV.getBit(2);
    }

    /**
     * Return the value of the Overflow flag
     */
    public boolean overflowFlag() {
        return NZCV.getBit(3);
    }

}
