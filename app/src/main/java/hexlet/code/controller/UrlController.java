package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;

public class UrlController {
    public static void root(Context ctx) {
        var page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashColor(ctx.consumeSessionAttribute("flashColor"));
        ctx.render("index.jte", Collections.singletonMap("page", page));
    }

    public static void createUrl(Context ctx) throws SQLException {
        var inputUrl = ctx.formParam("url");
        try {
            URL parsedUrl = new URL(inputUrl);
            if (parsedUrl == null || !parsedUrl.toURI().equals(parsedUrl.toURI().normalize())) {
                throw new MalformedURLException("Некорректный URL");
            }

            String normalizedUrl = buildNormalizedUrl(parsedUrl);

            if (UrlRepository.isExist(normalizedUrl)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashColor", "info");
                ctx.redirect(NamedRoutes.urlsPath());
                return;
            }
            Url newUrl = new Url(normalizedUrl);
            UrlRepository.saveUrl(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashColor", "success");
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashColor", "danger");
            ctx.redirect(NamedRoutes.rootPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getUrlEntities();
        var urlChecks = UrlRepository.findLastUrlCheck();

        var page = new UrlsPage(urls, urlChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashColor(ctx.consumeSessionAttribute("flashColor"));

        ctx.render("templates/urls/index.jte", Collections.singletonMap("page", page));
    }


    public static void showUrls(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findUrl(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        List<UrlCheck> urlChecks = UrlRepository.findUrlChecks(id);

        var page = new UrlPage(url, urlChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashColor(ctx.consumeSessionAttribute("flashColor"));

        ctx.render("templates/urls/show.jte", Collections.singletonMap("page", page));
    }

    public static void checkUrl(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findUrl(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        try {
            var response = Unirest.get(url.getName()).asString();
            var statusCode = response.getStatus();
            var document = Jsoup.parse(response.getBody());
            var title = document.title().isEmpty() ? null : document.title();
            var h1El = document.selectFirst("h1");
            var h1 = h1El == null ? null : h1El.ownText();
            var descriptionEl = document.selectFirst("meta[name=description]");
            var description = descriptionEl == null ? null : descriptionEl.attr("content");
            UrlCheck newCheck = new UrlCheck(statusCode, title, h1, description);
            newCheck.setUrlId(id);
            UrlRepository.saveCheck(newCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashColor", "success");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Ошибка при проверке URL");
            ctx.sessionAttribute("flashColor", "danger");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }

    public static String buildNormalizedUrl(URL parsedUrl) {
        return parsedUrl.getProtocol() + "://" + parsedUrl.getHost()
                + (parsedUrl.getPort() != -1 ? ":" + parsedUrl.getPort() : "");
    }
}