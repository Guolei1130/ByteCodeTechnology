package com.guolei.asm_plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;


/**
 * Created by Android Studio.
 * User: guolei
 * Email: 1120832563@qq.com
 * Date: 2019/4/8
 * Time: 10:36 PM
 * Desc:
 */
public class AsmDemo {
    public static void main(String[] args) throws IOException {
        File file = new File(
                "/Users/guolei/AndroidStudioProjects/ByteCode/plugin_asm/build/classes/java/main/com/guolei/asm_plugin/ContentReader.class");
        File outFile = new File(
                "/Users/guolei/AndroidStudioProjects/ByteCode/plugin_asm/build/classes/java/main/com/guolei/asm_plugin/ContentReader1.class");
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        fileOutputStream.write(referHackWhenInit(new FileInputStream(file)));
    }

    private static byte[] referHackWhenInit(InputStream inputStream) throws IOException {
        ClassReader cr = new ClassReader(inputStream);
        ClassWriter cw = new ClassWriter(cr, 0);

        ClassVisitor cv = new InjectClassVisitor(Opcodes.ASM4, cw);
        cr.accept(cv, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }

    static class InjectClassVisitor extends ClassVisitor {

        InjectClassVisitor(int i, ClassVisitor classVisitor) {
            super(i, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, final String name, String desc, String signature,
                                         String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
//      System.err.println("开始了-------->");
//      System.err.println(name);
//      System.err.println(desc);
//      System.err.println(signature);
//      System.err.println("--------->结束了");

            mv = new MethodVisitor(Opcodes.ASM5, mv) {
                @Override
                public void visitInsn(int opcode) {
//          System.err.println(name + ";" + opcode);
//          super.visitInsn(opcode);
                    if ("<init>".equals(name) && opcode == Opcodes.RETURN) {
//            Label l1 = new Label();
//            super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
//            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
//            super.visitJumpInsn(Opcodes.IFEQ, l1);
//            super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            super.visitLdcInsn(Type.getType("Lcom/android/internal/util/Predicate;"));
//            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
                        super.visitFieldInsn(Opcodes.GETSTATIC,
                                "java/lang/System",
                                "err",
                                "Ljava/io/PrintStream;");
                        super.visitLdcInsn("hello world");
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                "java/io/PrintStream",
                                "println",
                                "(Ljava/lang/String;)V",
                                false);

                        super.visitFieldInsn(Opcodes.GETSTATIC,
                                "java/lang/System",
                                "err",
                                "Ljava/io/PrintStream;");
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "java/lang/System",
                                "currentTimeMillis",
                                "()J",
                                false);
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                "java/lang/System",
                                "println",
                                "(J)V",
                                false
                        );
//            super.visitLabel(l1);
                    }
                    super.visitInsn(opcode);
                }

            };
            return new MethodAdapter(api, mv, access, name, desc);
        }
    }

    // aop demo
    private static class MethodAdapter extends AdviceAdapter {

        private String methodName;

        MethodAdapter(int i, MethodVisitor methodVisitor, int i1, String s, String s1) {
            super(i, methodVisitor, i1, s, s1);
            methodName = s;
        }

        @Override
        protected void onMethodEnter() {
            if (!methodName.equals("<init>")) {
                super.visitFieldInsn(Opcodes.GETSTATIC,
                        "java/lang/System",
                        "err",
                        "Ljava/io/PrintStream;");
                super.visitLdcInsn("method enter");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/io/PrintStream",
                        "println",
                        "(Ljava/lang/String;)V",
                        false);
                super.onMethodEnter();
            }
        }

        @Override
        protected void onMethodExit(int i) {
            if (!methodName.equals("<init>")) {
                super.visitFieldInsn(Opcodes.GETSTATIC,
                        "java/lang/System",
                        "err",
                        "Ljava/io/PrintStream;");
                super.visitLdcInsn("method exit");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/io/PrintStream",
                        "println",
                        "(Ljava/lang/String;)V",
                        false);
                super.onMethodExit(i);
            }
        }
    }

}
