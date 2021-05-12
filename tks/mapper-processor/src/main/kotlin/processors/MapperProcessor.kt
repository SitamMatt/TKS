package processors

import annotations.MapperAnnotation
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.classinspector.elements.ElementsClassInspector
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import java.io.File
import java.util.function.Consumer
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("annotations.MapperAnnotation")
@SupportedOptions(MapperProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
@AutoService(Processor::class)
class MapperProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    val mappers = hashMapOf("AccessionNumber" to "AccessionNumberMapper")

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"] ?: return false

        val classes = roundEnv!!.getElementsAnnotatedWith(MapperAnnotation::class.java)
        classes.forEach(Consumer { x -> doit(x) })
//        val fileBuilder = FileSpec.builder("package", "fileName")
        // add whatever you need to the file
//        fileBuilder.build().writeTo(File(kaptKotlinGeneratedDir))
        return true
    }

    @OptIn(KotlinPoetMetadataPreview::class)
    private fun doit(x: Element?) {
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]

        val env = this.processingEnv
        val inspector = ElementsClassInspector.create(env.elementUtils, env.typeUtils)
        val className = x?.simpleName
        var srcParams: List<Element>? = null
        var targetConst: ExecutableElement? = null
        var typeName: TypeName? = null
        var returnElem: TypeName? = null
        var targetElem: TypeElement? = null
        var methods = x?.enclosedElements?.filter { x -> x.kind == ElementKind.METHOD }
        val classes = methods?.forEach {
            val elem = (it as ExecutableElement).returnType
            returnElem = elem.asTypeName()
            val mirr = env.typeUtils.asElement(elem) as TypeElement
            targetElem = mirr
            val mem = env.elementUtils.getAllMembers(mirr)
            val const = mem.filter { l -> l.kind == ElementKind.CONSTRUCTOR }.firstOrNull()
            targetConst = const as ExecutableElement
//            srcParams = constructor.parameters
            val srcElem = (it as ExecutableElement).parameters[0].asType()
            typeName = srcElem.asTypeName()
            val mirr2 = env.typeUtils.asElement(srcElem) as TypeElement
            val mem2 = env.elementUtils.getAllMembers(mirr2).filter { g -> g.kind == ElementKind.FIELD }
            srcParams = mem2
        }

        var funBuilder = FunSpec.builder("toDomain")
            .addParameter(ParameterSpec.builder("src", typeName!!).build())
            .returns(returnElem!!)
            .addModifiers(KModifier.OVERRIDE)

        var str = "return ${targetElem?.simpleName}("
        val pars = targetConst?.parameters?.map {
            val typeN = it.asType().asTypeName().toString()
            if(it.simpleName.toString() == "accessionNumber"){
                return@map "accessionMapper.fromString${it.simpleName}"
            }
            "src.${it.simpleName}"
        }
        val params2 = pars?.joinToString(",")
        str += "$params2)"

        val map = env.elementUtils.getTypeElement("AccessionNumberMapper")

        funBuilder.addCode(str)
        val classBuilder = TypeSpec
            .classBuilder("${x?.simpleName}Impl")
            .addSuperinterface((x as TypeElement).asType().asTypeName())
            .addProperty("accessionNumberMapper", map.asType().asTypeName(), KModifier.PRIVATE)
            .addFunction(
                funBuilder.build()
            )
            .build()
        val fileBuilder = FileSpec.builder("package", "fileName")
        fileBuilder.addType(classBuilder)
        // add whatever you need to the file
        fileBuilder.build().writeTo(File(kaptKotlinGeneratedDir))
    }


}