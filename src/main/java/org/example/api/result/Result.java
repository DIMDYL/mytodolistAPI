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
  public static <T> Result<T> success(String msg,T object){
    Result<T> result = new Result<>();
    result.setCode(1);
    result.setMsg(msg);
    result.setData(object);
    return result;
  }
  public static <T> Result<T> error(String msg) {
    Result<T> result = new Result();
    result.setMsg(msg);
    result.setCode(Integer.valueOf(0));
    return result;
  }
}
