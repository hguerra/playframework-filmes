package controllers;

import models.Diretor;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static models.Diretor.find;

/**
 * Created by heitor on 13/04/16.
 */
public class DiretorCRUD extends Controller
{
    @Inject
    private FormFactory formFactory;

    private Form<Diretor> getFormDiretor()
    {
        return formFactory.form(Diretor.class);
    }

    /**
     * Routes
     *
     * @return
     */
    public Result lista()
    {
        List<Diretor> diretores = find.findList();
        return ok(views.html.diretor.render(diretores, "Teste"));
    }

    public Result novoDiretor()
    {
        return ok(views.html.novoDiretor.render(getFormDiretor()));
    }

    public Result detalhar(Long id)
    {
        Form<Diretor> diretorForm = getFormDiretor().fill(find.byId(id));
        return ok(views.html.alterarDiretor.render(id, diretorForm));
    }

    public Result alterar(Long id)
    {
        Diretor diretor = Diretor.find.byId(id);

        System.out.println("NOME>>" + diretor.nome);
        System.out.println("ID>>" + diretor.id);

        Form<Diretor> alterarForm = getFormDiretor().bindFromRequest();

        //set novo nome
        diretor.nome = alterarForm.get().nome;

        System.out.println("NOME_ALTERAR>>" + diretor.nome);

        //update
        diretor.update();

        flash("sucesso", "Diretor " + diretor.nome + " alterado com sucesso");
        return redirect(routes.DiretorCRUD.lista());
    }

    public Result gravar()
    {
        Form<Diretor> form = getFormDiretor().bindFromRequest();

        if (form.hasErrors())
        {
            flash("erro", "Foram identificados problemas no cadastro");
            return badRequest(views.html.novoDiretor.render(getFormDiretor()));
        }

        Diretor diretor = form.get();

        System.out.println("NOME>>" + diretor.nome);

        diretor.save();

        flash("sucesso", "Registro gravado com sucesso");

        return redirect(routes.DiretorCRUD.lista());
    }

    public Result remover(Long id)
    {
        try
        {
            Diretor.find.ref(id).delete();
            flash("sucesso","Diretor removido com sucesso");
        }catch (Exception e)
        {
            flash("erro",play.i18n.Messages.get("global.erro"));
        }
        return lista();
    }

}
