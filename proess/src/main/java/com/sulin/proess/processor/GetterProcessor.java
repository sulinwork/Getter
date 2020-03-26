package com.sulin.proess.processor;

import com.sulin.proess.annotition.*;
import com.google.auto.service.AutoService;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.*;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.sulin.proess.annotition.Getter"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetterProcessor extends AbstractProcessor {

    private Messager messager;
    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.out.println("----------init--------------");
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        System.out.println("GetterProcessor注解处理器处理中");
//        messager.printMessage(Diagnostic.Kind.NOTE, "GetterProcessor注解处理器处理中");
//        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Getter.class);
//        for (Element element : elementsAnnotatedWith) {
//            JCTree tree = trees.getTree(element);
//            System.out.println("获取到" + element.toString() + "语法树");
//            System.out.println("获取到" + element.getSimpleName() + "语法树");
//
//
//        }
//        return true;
//    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "GetterProcessor注解处理器处理中");

        roundEnv.getElementsAnnotatedWith(Getter.class).stream().map(element -> trees.getTree(element)).forEach(t -> t.accept(new TreeTranslator() {
            @Override
            public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl());
                super.visitClassDef(jcClassDecl);
            }
        }));
        return true;
    }

//    @Override
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        System.out.println("----------process----------");
//        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(Getter.class);
//        set.forEach(element -> {
//            Method annotationsByType = element.getAnnotation(Method.class);
//            for (CollectionMethod collection : annotationsByType.collections()) {
//                System.out.println("---:" + collection.path());
//            }
//            for (InstanceMethod collection : annotationsByType.instances()) {
//                System.out.println("---:" + collection.path());
//            }
//            JCTree jcTree = trees.getTree(element);
//            jcTree.accept(new TreeTranslator() {
//                @Override
//                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
//                    try{
//                        //方法的访问级别
//                        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
//                        //设置返回值类型
//                        JCTree.JCExpression returnMethodType = treeMaker.Type((Type) (Class.forName("java.util.List").newInstance()));
//                        //设置参数列表
//                        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER, List.nil()),
//                                names.fromString("id"), treeMaker.Ident(names.fromString("Object")), null);
//                        //设置入参
//                        List<JCTree.JCVariableDecl> parameters = List.of(param);
//
//                        //other
//                        //定义返回值类型
//                        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
//                        statements.append(treeMaker.Exec(treeMaker.Assign(treeMaker.Select(treeMaker.Ident(names.fromString("this")), names.fromString("doItems")), treeMaker.Ident(names.fromString("doItems")))));
//                        //定义方法体
//                        JCTree.JCBlock methodBody = treeMaker.Block(0, statements.toList());
//                        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
//
//                        JCTree.JCMethodDecl doItems = treeMaker.MethodDef(modifiers, names.fromString("doItems"), returnMethodType, methodGenericParams, parameters, List.nil(), methodBody, null);
//                        //构建新方法
//                        JCTree.JCMethodDecl doItem = treeMaker.MethodDef(modifiers, names.fromString("doItems"), returnMethodType, methodGenericParams, parameters, List.nil(), methodBody, null);
//                        jcClassDecl.defs = jcClassDecl.defs.prepend(doItem);
//                    }catch (Exception e){
//
//                    }
//                    super.visitClassDef(jcClassDecl);
//                }
//
//            });
//        });
//        return true;
//    }

    /**
     * 创建get方法
     *
     * @param
     * @return
     */
    private JCTree.JCMethodDecl makeGetterMethodDecl() {
        //方法的访问级别
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        //方法名称
        Name methodName = names.fromString("doItems");
        //设置返回值类型
//        JCTree.JCExpression returnMethodType = memberAccess("java.util.List");
//        new JCTree.JCTypeApply();
        JCTree.JCExpression returnMethodType = treeMaker.TypeApply(memberAccess("java.util.List"), List.of(memberAccess("java.lang.String")));


        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();

        statements.append(treeMaker.Return(treeMaker.Literal(TypeTag.BOT,null)));
        //设置方法体
        JCTree.JCBlock methodBody = treeMaker.Block(0, statements.toList());
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        List<JCTree.JCVariableDecl> parameters = List.nil();
        List<JCTree.JCExpression> throwsClauses = List.nil();
        //构建方法
        return treeMaker.MethodDef(modifiers, methodName, returnMethodType, methodGenericParams, parameters, throwsClauses, methodBody, null);
    }

    private Name getNewMethodName(String name) {
        String s = name.toString();
        return names.fromString("set" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }

    /**
     * 创建set方法
     *
     * @param jcVariableDecl
     * @return
     */
    private JCTree.JCMethodDecl makeSetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        try {
            //方法的访问级别
            List<JCTree.JCAnnotation> annotations = List.of(treeMaker.Annotation(memberAccess("com.sulin.proess.annotition.MyController"),
                    List.of(treeMaker.Assign(treeMaker.Ident(names.fromString("name")), treeMaker.Literal("NBNBNB")))));
            JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotations);
            //定义方法名
            Name methodName = setMethodName(names.fromString("doItems"));
            //定义返回值类型
//            JCTree.JCExpression returnMethodType = treeMaker.Type((Type) (Class.forName("com.sun.tools.javac.code.Type$JCVoidType").newInstance()));
//            System.out.println(returnMethodType.toString());
//            JCTree.JCExpression returnMethodType = memberAccess("java.util.List");
            JCTree.JCExpression returnMethodType = memberAccess("com.sulin.use.bean.Student");
            System.out.println("returnMethodType:" + returnMethodType.toString());
//            JCTree.JCExpression returnMethodType=treeMaker.Type()
            //搞定body
            ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
            statements.append(treeMaker.Exec(
                    treeMaker.Assign(
                            treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()),
                            treeMaker.Ident(jcVariableDecl.getName()))
            ));
            //定义入参
            statements.append(treeMaker.Return(treeMaker.Literal(TypeTag.CLASS, null)));
            //定义方法体
            JCTree.JCBlock methodBody = treeMaker.Block(0, statements.toList());
            List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
            //定义入参
            JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER, List.nil()), jcVariableDecl.name, jcVariableDecl.vartype, null);
            //设置入参
            List<JCTree.JCVariableDecl> parameters = List.of(param);
            List<JCTree.JCExpression> throwsClauses = List.nil();
            //构建新方法
            JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(modifiers, methodName, returnMethodType, methodGenericParams, parameters, throwsClauses, methodBody, null);

            return jcMethodDecl;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;

    }

    private Name setMethodName(Name name) {
        String s = name.toString();
        return names.fromString("set" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }


    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(componentArray[i]));
        }
        return expr;
    }
}
