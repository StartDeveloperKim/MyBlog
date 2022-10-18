package my.blog.category.exception;

public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException() {}
    public DuplicateCategoryException(String s) {super(s);}
}
