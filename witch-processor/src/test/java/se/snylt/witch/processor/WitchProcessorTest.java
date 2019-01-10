package se.snylt.witch.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.junit.Assert.fail;

public class WitchProcessorTest {

    private final String docsUrl = "https://sedstrom.github.io/Witch-Android/";

    private final String readMore = "Read more at: " + docsUrl;

    private Compilation compile(String resourceName) {
        JavaFileObject test = JavaFileObjects.forResource(resourceName);
        return javac().withProcessors(new WitchProcessor(false)).compile(test);
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
    public void simpleBindMethod() {
        Compilation compilation = compile("SimpleBindMethod.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindMethodData() {
        Compilation compilation = compile("SimpleBindMethodData.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindMethodView() {
        Compilation compilation = compile("SimpleBindMethodView.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindMethodViewData() {
        Compilation compilation = compile("SimpleBindMethodViewData.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindMethodViewDataData() {
        Compilation compilation = compile("SimpleBindMethodViewDataData.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleBindMethodDataData() {
        Compilation compilation = compile("SimpleBindMethodDataData.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void simpleSetupMethod() {
        Compilation compilation = compile("SimpleSetupMethod.java");
        assertThat(compilation).succeeded();
    }

    @Test
    public void errorDataMethodPrivate() {
        Compilation compilation = compile("ErrorDataMethodPrivate.java");
        assertThat(compilation).hadErrorContaining("ErrorDataMethodPrivate");
        assertThat(compilation).hadErrorContaining("java.lang.String text() is not a valid data accessor");
        assertThat(compilation).hadErrorContaining("Make sure accessor conforms to:");
        assertThat(compilation).hadErrorContaining("- Is field or method");
        assertThat(compilation).hadErrorContaining("- Is not private nor protected");
        assertThat(compilation).hadErrorContaining("- Has no parameters");
        assertThat(compilation).hadErrorContaining("- Has a non-void return type");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorDataFieldPrivate() {
        Compilation compilation = compile("ErrorDataFieldPrivate.java");
        assertThat(compilation).hadErrorContaining("ErrorDataFieldPrivate");
        assertThat(compilation).hadErrorContaining("java.lang.String text is not a valid data accessor");
        assertThat(compilation).hadErrorContaining("Make sure accessor conforms to:");
        assertThat(compilation).hadErrorContaining("- Is field or method");
        assertThat(compilation).hadErrorContaining("- Is not private nor protected");
        assertThat(compilation).hadErrorContaining("- Has no parameters");
        assertThat(compilation).hadErrorContaining("- Has a non-void return type");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorBindMethodPrivate() {
        Compilation compilation = compile("ErrorBindMethodPrivate.java");
        assertThat(compilation).hadErrorContaining("ErrorBindMethodPrivate");
        assertThat(compilation).hadErrorContaining("void text(android.view.View,java.lang.String) is not accessible");
        assertThat(compilation).hadErrorContaining("Make sure bind method is not private nor protected");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorBindMethodWrongParameterCount() {
        Compilation compilation = compile("ErrorBindMethodWrongParameterCount.java");
        assertThat(compilation).hadErrorContaining("ErrorBindMethodWrongParameterCount");
        assertThat(compilation).hadErrorContaining("void text(android.view.View,java.lang.String,java.lang.String,java.lang.String) is not a valid bind method");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        assertValidBindMethodSignatures(compilation, "text");
        printErrors(compilation);
    }

    @Test
    public void errorBindMethodIncompatibleHistoryType() {
        Compilation compilation = compile("ErrorIncompatibleHistoryType.java");
        assertThat(compilation).hadErrorContaining("ErrorIncompatibleHistoryType");
        assertThat(compilation).hadErrorContaining("void text(android.view.View,java.lang.String,java.lang.Integer) is not a valid bind method");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        assertValidBindMethodSignatures(compilation, "text");
        printErrors(compilation);
    }

    private void assertValidBindMethodSignatures(Compilation compilation, String methodName) {
        assertThat(compilation).hadErrorContaining("Valid bind method signatures are:");
        assertThat(compilation).hadErrorContaining(String.format("%s()", methodName));
        assertThat(compilation).hadErrorContaining(String.format("%s(View)", methodName));
        assertThat(compilation).hadErrorContaining(String.format("%s(View, Data)", methodName));
        assertThat(compilation).hadErrorContaining(String.format("%s(View, Data, Data)", methodName));
        assertThat(compilation).hadErrorContaining(String.format("%s(Data)", methodName));
        assertThat(compilation).hadErrorContaining(String.format("%s(Data, Data)", methodName));
        printErrors(compilation);
    }

    @Test
    public void errorIncompatibleDataTypes() {
        Compilation compilation = compile("ErrorIncompatibleDataTypes.java");
        assertThat(compilation).hadErrorContaining("ErrorIncompatibleDataTypes");
        assertThat(compilation).hadErrorContaining("void bind(android.view.View,java.lang.Integer)");
        assertThat(compilation).hadErrorContaining("is invalid bind method for:");
        assertThat(compilation).hadErrorContaining("java.lang.String text");
        assertThat(compilation).hadErrorContaining("Data types are incompatible");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorDataHasParameters() {
        Compilation compilation = compile("ErrorDataHasParameters.java");
        assertThat(compilation).hadErrorContaining("ErrorDataHasParameters");
        assertThat(compilation).hadErrorContaining("java.lang.String text(java.lang.String) is not a valid data accessor");
        assertThat(compilation).hadErrorContaining("Make sure accessor conforms to:");
        assertThat(compilation).hadErrorContaining("- Is field or method");
        assertThat(compilation).hadErrorContaining("- Is not private nor protected");
        assertThat(compilation).hadErrorContaining("- Has no parameters");
        assertThat(compilation).hadErrorContaining("- Has a non-void return type");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
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
        assertThat(compilation).hadErrorContaining("text(android.view.View) has invalid value \"sometimes\" for @BindWhen");
        assertThat(compilation).hadErrorContaining("Valid values are");
        assertThat(compilation).hadErrorContaining("BindWhen.NOT_SAME");
        assertThat(compilation).hadErrorContaining("BindWhen.NOT_EQUALS");
        assertThat(compilation).hadErrorContaining("BindWhen.ALWAYS");
        assertThat(compilation).hadErrorContaining("BindWhen.ONCE");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorBindDataMissingData() {
        Compilation compilation = compile("ErrorBindDataMissingData.java");
        assertThat(compilation).hadErrorContaining("ErrorBindDataMissingData");
        assertThat(compilation).hadErrorContaining("Missing @Data-annotated field named \"missingData\" for bind method void bind(java.lang.String)");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorBindWhenOnNonBind() {
        Compilation compilation = compile("ErrorBindWhenOnNonBind.java");
        assertThat(compilation).hadErrorContaining("ErrorBindWhenOnNonBind");
        assertThat(compilation).hadErrorContaining("Invalid use of @BindWhen at java.lang.String data.");
        assertThat(compilation).hadErrorContaining("@BindWhen must be combined with a @Bind or @BindData annotation.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorBindNullOnNonBind() {
        Compilation compilation = compile("ErrorBindNullOnNonBind.java");
        assertThat(compilation).hadErrorContaining("ErrorBindNullOnNonBind");
        assertThat(compilation).hadErrorContaining("Invalid use of @BindNull at java.lang.String data.");
        assertThat(compilation).hadErrorContaining("@BindNull must be combined with a @Bind or @BindData annotation.");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorMultipleBindsSetupBind() {
        Compilation compilation = compile("ErrorMultipleBindsSetupBind.java");
        assertThat(compilation).hadErrorContaining("ErrorMultipleBindsSetupBind");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        assertMultipleBindAnnotationsError(compilation, "bind");
        printErrors(compilation);
    }

    @Test
    public void errorMultipleBindsBindDataBind() {
        Compilation compilation = compile("ErrorMultipleBindsBindDataBind.java");
        assertThat(compilation).hadErrorContaining("ErrorMultipleBindsBindDataBind");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        assertMultipleBindAnnotationsError(compilation, "bind");
        printErrors(compilation);
    }

    @Test
    public void errorMultipleBindsBindDataSetup() {
        Compilation compilation = compile("ErrorMultipleBindsBindDataSetup.java");
        assertThat(compilation).hadErrorContaining("ErrorMultipleBindsBindDataSetup");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        assertMultipleBindAnnotationsError(compilation, "bind");
        printErrors(compilation);
    }

    private void assertMultipleBindAnnotationsError(Compilation compilation, String forMethodName) {
        assertThat(compilation).hadErrorContaining(String.format("More than one bind annotation used for void %s()", forMethodName));
        assertThat(compilation).hadErrorContaining("Make sure only one of the following annotations are used:");
        assertThat(compilation).hadErrorContaining("@Bind");
        assertThat(compilation).hadErrorContaining("@BindData");
        assertThat(compilation).hadErrorContaining("@Setup");
        printErrors(compilation);
    }

    @Test
    public void errorBindViewWithoutId() {
        Compilation compilation = compile("ErrorBindViewWithoutId.java");
        assertThat(compilation).hadErrorContaining("ErrorBindViewWithoutId");
        assertThat(compilation).hadErrorContaining("void bind(android.view.View) takes a view but has no id declared in annotation.");
        assertThat(compilation).hadErrorContaining("Example usage:");
        assertThat(compilation).hadErrorContaining("@Bind(id = R.id.title)");
        assertThat(compilation).hadErrorContaining("@Setup(id = R.id.title)");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    @Test
    public void errorBindWithIdWithoutView() {
        Compilation compilation = compile("ErrorBindWithIdWithoutView.java");
        assertThat(compilation).hadErrorContaining("ErrorBindWithIdWithoutView");
        assertThat(compilation).hadErrorContaining("void bind() has id declared in annotation but takes no view.");
        assertThat(compilation).hadErrorContaining("Example usage:");
        assertThat(compilation).hadErrorContaining("@Bind(id = R.id.title)");
        assertThat(compilation).hadErrorContaining("void bind(TextView title)");
        assertThat(compilation).hadErrorContaining(String.format(readMore));
        printErrors(compilation);
    }

    private void printErrors(Compilation compilation) {
        String file = compilation.sourceFiles().get(0).getName();
        String name = file.substring(file.lastIndexOf("/") + 1);
        System.out.println("=====================================");
        System.out.println("Error for " + name +":");
        for (Diagnostic e: compilation.errors()) {
            System.out.println(e.getMessage(Locale.getDefault()));
        }
    }
}