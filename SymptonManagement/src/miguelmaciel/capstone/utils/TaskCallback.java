/* 
**
** Copyright 2014, Jules White
**
** 
*/
package miguelmaciel.capstone.utils;

public interface TaskCallback<T> {

    public void success(T result);

    public void error(Exception e);

}
