<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>${empty book.id ? 'Thêm' : 'Sửa'} sách | BookVerse</title>
<section class="edit"><h1>${empty book.id ? 'Thêm sách' : 'Cập nhật sách'}</h1>
<form method="post" action="book-save" enctype="multipart/form-data">
<input type="hidden" name="id" value="${book.id}">
<label>Tiêu đề<input name="title" value="${book.title}" required></label>
<label>ISBN<input name="isbn" value="${book.isbn}" required></label>
<label>Tác giả<select name="authorId"><c:forEach items="${authors}" var="a"><option value="${a.id}" ${book.author.id eq a.id ? 'selected' : ''}>${a.name}</option></c:forEach></select></label>
<label>Nhà xuất bản<input name="publisher" value="${book.publisher}" required></label>
<label>Ngày xuất bản<input type="date" name="publisherDate" value="${book.publisherDate}" required></label>
<label>Số lượng<input type="number" min="0" name="quantity" value="${book.quantity}" required></label>
<label>Giá (VND)<input type="number" min="0" step="1000" name="price" value="${book.price}" required></label>
<label>Tải ảnh bìa<input type="file" name="cover" accept="image/jpeg,image/png,image/webp,image/gif" ${empty book.id ? 'required' : ''}></label>
<c:if test="${not empty book.coverUrl}"><p>Ảnh hiện tại: <a href="${book.coverUrl}" target="_blank">xem ảnh bìa</a></p></c:if>
<label>Mô tả<textarea name="description">${book.description}</textarea></label><button>Lưu sách</button>
</form></section>
