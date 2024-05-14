package org.example.api.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
  private Integer code;
  
  private String msg;
  
  private T data;

  public static <T> Result<T> success() {
    Result<T> result = new Result<>();
    result.code = Integer.valueOf(1);
    return result;
  }
  
  public static <T> Result<T> success(T object) {
    Result<T> result = new Result<>();
    result.data = object;
    result.code = Integer.valueOf(1);
    return result;
  }
  
  public static <T> Result<T> success(String msg) {
   Result<T> result = new Result<>();
    result.msg = msg;
    result.code = Integer.valueOf(1);
    return result;
  }
  
  public static <T> Result<T> error(String msg) {
    Result<T> result = new Result();
    result.msg = msg;
    result.code = Integer.valueOf(0);
    return result;
  }
}
