package io.burt.jmespath.node;

import java.util.List;
import java.util.LinkedList;

import io.burt.jmespath.Adapter;
import io.burt.jmespath.JmesPathType;

public class FlattenArrayNode<T> extends Node<T> {
  public FlattenArrayNode(Adapter<T> runtime) {
    super(runtime);
  }

  @Override
  public T search(T input) {
    if (runtime.typeOf(input) == JmesPathType.ARRAY) {
      List<T> elements = runtime.toList(input);
      List<T> flattened = new LinkedList<>();
      for (T element : elements) {
        final JmesPathType elementType = runtime.typeOf(element);
        if (elementType == JmesPathType.ARRAY) {
          flattened.addAll(runtime.toList(element));
        } else if (elementType != JmesPathType.NULL) {
          flattened.add(element);
        }
      }
      return runtime.createArray(flattened);
    } else {
      return runtime.createNull();
    }
  }

  @Override
  protected boolean internalEquals(Object o) {
    return true;
  }

  @Override
  protected int internalHashCode() {
    return 19;
  }
}
