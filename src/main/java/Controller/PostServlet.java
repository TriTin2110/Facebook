package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import HibernateUtil.HibernateUtil;
import Model.Post;

@WebServlet("/posting")
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final String UPLOAD_DIRECTORY = "uploads";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<Post> posts = session.createQuery("from Post", Post.class).list();
			req.setAttribute("posts", posts);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving posts");
			return;
		}
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("content");
		Part filePart = req.getPart("image");

		if (filePart == null || filePart.getSubmittedFileName().isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "File part is missing or file name is empty");
			return;
		}

		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		filePart.write(uploadPath + File.separator + fileName);

		Post post = new Post();
		post.setPostId("P2");
		post.setPostContent(content);
		post.setPostImage(UPLOAD_DIRECTORY + "/" + fileName);

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Transaction transaction = session.beginTransaction();
			session.save(post);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving post");
			return;
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = gson.toJson(post);
		resp.getWriter().write(json);
	}
}
