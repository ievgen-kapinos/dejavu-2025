package org.example;

import org.junit.jupiter.params.converter.AnnotationBasedArgumentConverter;
import org.junit.jupiter.params.converter.ArgumentConversionException;

public class MyTestConverterImpl extends AnnotationBasedArgumentConverter<MyTestConverter> {

    @Override
    protected Object convert(Object source, Class<?> targetType, MyTestConverter annotation) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new ArgumentConversionException("Input is null");
        }
        var input = (String) source;

        if (targetType == int[].class) {
            return MyTestUtils.parseArray(input);
        } else if (targetType == int[][].class) {
            return MyTestUtils.parseArray2D(input);
        } else if (targetType == boolean[].class) {
            return MyTestUtils.parseArrayBoolean(input);
        } else if (targetType == TreeNode.class) {
            return MyTestUtils.parseTree(input);
        } else {
            throw new ArgumentConversionException("Unsupported target type: " + targetType);
        }
    }
}
