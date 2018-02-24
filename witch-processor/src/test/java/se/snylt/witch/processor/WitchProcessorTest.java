package se.snylt.witch.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

public class WitchProcessorTest {

    @Test
    public void test() {
        JavaFileObject test = JavaFileObjects.forResource("Test.java");
        Compilation compilation =
                javac()
                .withOptions()
                .withProcessors(new WitchProcessor())
                .compile(test);

        assertThat(compilation).succeeded();
    }

}