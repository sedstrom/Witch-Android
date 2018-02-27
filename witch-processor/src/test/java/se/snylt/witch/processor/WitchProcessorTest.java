package se.snylt.witch.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;
import javax.tools.JavaFileObject;
import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

public class WitchProcessorTest {

    private final String docsUrl = "https://sedstrom.github.io/Witch-Android/";

    private final String readMore = "Read more at: " + docsUrl;

    private Compilation compile(String resourceName) {
        JavaFileObject test = JavaFileObjects.forResource(resourceName);
        return javac().withProcessors(new WitchProcessor()).compile(test);
    }

    @Test
    public void simpleDataBind() {
        Compilation compilation = compile("SimpleDataBind.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindData() {
        Compilation compilation = compile("SimpleBindData.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindDataMethod() {
        Compilation compilation = compile("SimpleBindDataMethod.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void errorDataMissingBind() {
        Compilation compilation = compile("ErrorDataMissingBind.java");
        assertThat(compilation).hadErrorContaining("ErrorDataMissingBind");
        assertThat(compilation).hadErrorContaining("Missing @Bind for @Data text.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorDataMethodPrivate() {
        Compilation compilation = compile("ErrorDataMethodPrivate.java");
        assertThat(compilation).hadErrorContaining("ErrorDataMethodPrivate");
        assertThat(compilation).hadErrorContaining("text() is not a valid data accessor");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorDataFieldPrivate() {
        Compilation compilation = compile("ErrorDataFieldPrivate.java");
        assertThat(compilation).hadErrorContaining("ErrorDataFieldPrivate");
        assertThat(compilation).hadErrorContaining("text is not a valid data accessor");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorBindMethodPrivate() {
        Compilation compilation = compile("ErrorBindMethodPrivate.java");
        assertThat(compilation).hadErrorContaining("ErrorBindMethodPrivate");
        assertThat(compilation).hadErrorContaining("text(android.view.View,java.lang.String) is not accessible.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorBindMethodWrongParameterCount() {
        Compilation compilation = compile("ErrorBindMethodWrongParameterCount.java");
        assertThat(compilation).hadErrorContaining("ErrorBindMethodWrongParameterCount");
        assertThat(compilation).hadErrorContaining("text(android.view.View) has wrong number of parameters.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorBindMethodFirstParameterNotView() {
        Compilation compilation = compile("ErrorBindMethodFirstParameterNotView.java");
        assertThat(compilation).hadErrorContaining("ErrorBindMethodFirstParameterNotView");
        assertThat(compilation).hadErrorContaining("text(java.lang.String,android.view.View) has invalid view type.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorIncompatibleDataTypes() {
        Compilation compilation = compile("ErrorIncompatibleDataTypes.java");
        assertThat(compilation).hadErrorContaining("ErrorIncompatibleDataTypes");
        assertThat(compilation).hadErrorContaining("text and text(android.view.View,java.lang.Integer) have incompatible data types.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorDataHasParameters() {
        Compilation compilation = compile("ErrorDataHasParameters.java");
        assertThat(compilation).hadErrorContaining("ErrorDataHasParameters");
        assertThat(compilation).hadErrorContaining("text(java.lang.String) is not a valid data accessor.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void superDataType() {
        Compilation compilation = compile("SuperDataType.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void primitiveDataType() {
        Compilation compilation = compile("PrimitiveDataType.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void parameterizedDataTypes() {
        Compilation compilation = compile("ParemeterizedDataTypes.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void errorInvalidBindWhen() {
        Compilation compilation = compile("ErrorInvalidBindWhen.java");
        assertThat(compilation).hadErrorContaining("ErrorInvalidBindWhen");
        assertThat(compilation).hadErrorContaining("text has invalid value \"sometimes\" for @BindWhen.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

    @Test
    public void errorConflictingBindWhen() {
        Compilation compilation = compile("ErrorConflictingBindWhen.java");
        assertThat(compilation).hadErrorContaining("ErrorConflictingBindWhen");
        assertThat(compilation).hadErrorContaining("@BindWhen is defined multiple times at text(android.view.View,java.lang.String).");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
    }

}