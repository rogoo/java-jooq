package br.rosa.rjooq;

import static org.jooq.impl.DSL.using;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.stream.Stream;

import org.jooq.CloseableResultQuery;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import br.rosa.rjooq.dto.AuthorDTO;
import br.rosa.rjooq.dto.AuthorRec;
import br.rosa.rjooq.dto.MyAuthorJPAAnnotated;
import br.rosa.rjooq.dto.inter.PostProxy;
import br.rosa.rjooq.generated.Keys;
import br.rosa.rjooq.generated.Tables;
import br.rosa.rjooq.generated.tables.Author;
import br.rosa.rjooq.generated.tables.records.AuthorRecord;
import br.rosa.rjooq.generated.tables.records.PostRecord;

/**
 * Rogoo
 */
public class App {

	static DSLContext context = null;

	public static void main(String[] args) {
		String user = "root";
		String pass = "root";
		String url = "jdbc:mysql://localhost:3306";

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			context = using(conn, SQLDialect.MYSQL);
			// context.configuration().settings().withRenderImplicitJoinToManyType(RenderImplicitJoinType.LEFT_JOIN);

			generateDDL();
			// listTables();
			// optimisticKey(conn);
			// fetchParent(4);
			// removerSeCondicaoAtendida("rosa");
			// deletandoAuthor(5);
			// salvandoUmAuthor("Blabla", "do Blu blu");
			// reutilizandoPreparedStatements();
			// lazyFetchingStream();
			// lazyFetching();
			// usandoPojoParaInclusao();
			// usandoInterfaceProxyParaCarregamento();
			// usandoPOJOParaCarregamento();
			// carregandoRecordDTO();
			// alterandoRetorno();
			// usandoJPAAnnotatedPOJOParaCarregamento();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generateDDL() {
		System.out.println(context.meta(Tables.AUTHOR, Tables.POST));
	}

	public static void listTables() {
		context.meta().filterSchemas(s -> s.getName().equalsIgnoreCase("library")).getTables()
				.forEach(System.out::println);
	}

	public static void optimisticKey(Connection conn) {
		DSLContext optContext = DSL.using(conn, SQLDialect.MYSQL,
				new Settings().withExecuteWithOptimisticLocking(true));

		PostRecord post1 = optContext.fetchOne(Tables.POST, Tables.POST.ID.eq(1));
		PostRecord post2 = optContext.fetchOne(Tables.POST, Tables.POST.ID.eq(1));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		post1.setAssunto("Noixxxxx");
		post1.store();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		post2.setAssunto("Noixxxxx 2");
		post2.store();
	}

	public static void fetchParent(Integer id) {
		PostRecord post = context.fetchOne(Tables.POST, Tables.POST.ID.eq(id));
		AuthorRecord author = post.fetchParent(Keys.FK_POST_AUTHOR);
		System.out.println(author);
	}

	public static void removerSeCondicaoAtendida(String lastName) {
		for (PostRecord post : context.fetch(Tables.POST, Tables.POST.DESCRICAO.likeIgnoreCase("%leg%"))) {
			if (lastName.equalsIgnoreCase(post.fetchParent(Keys.FK_POST_AUTHOR).getLastName())) {
				System.out.println(post);
				// post.delete();
			}
		}
	}

	public static void atualizarRegistroObsoleto(Integer id) {
		PostRecord post = context.fetchOne(Tables.POST, Tables.POST.ID.eq(id));

		// Aqui ocorre a mágica
		post.refresh();
	}

	public static void deletandoAuthor(Integer id) {
		context.fetchOne(Tables.AUTHOR, Tables.AUTHOR.ID.eq(id)).delete();
	}

	public static void salvandoUmAuthor(String firstName, String lastName) {
		AuthorRecord author = context.newRecord(Tables.AUTHOR);

		author.setFirstName(firstName);
		author.setLastName(lastName);

		// o método STORE pode fazer um INSERT ou UPDATE
		System.out.println("Foi salvo com sucesso: " + author.store());
		System.out.println("Qual PK? Foiiiii a " + author.getId());
	}

	public static void reutilizandoPreparedStatements() {
		try (CloseableResultQuery<Record> query = context.select().from(Tables.AUTHOR).keepStatement(true)) {
			System.out.println(query.fetchAny());
			Thread.sleep(1400);
			System.out.println(query.fetchAny());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void lazyFetchingStream() {
		try (Stream<AuthorRecord> stream = context.selectFrom(Tables.AUTHOR).stream()) {
			stream.forEach(System.out::println);
		}
	}

	public static void lazyFetching() {
		try (Cursor<AuthorRecord> cursor = context.selectFrom(Tables.AUTHOR).fetchLazy()) {
			while (cursor.hasNext()) {
				Thread.sleep(1000);
				System.out.println(cursor.fetchNext());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void usandoPojoParaInclusao() {
		AuthorDTO dto = AuthorDTO.newInstance(5, "Djalma", "Sogrinnn");
		AuthorRecord authorDB = context.newRecord(Tables.AUTHOR, dto);
		System.out.println(authorDB);
		// implicitly
		// authorDB.store();

		// explicitly
		context.executeInsert(authorDB);
	}

	public static void carregandoRecordDTO() {
		Author a = Tables.AUTHOR.as("a");

		context.select(a.ID, a.FIRST_NAME, a.LAST_NAME).from(a).fetch(Records.mapping(AuthorRec::new)).forEach(r -> {
			System.out.println(r);
		});

	}

	public static void alterandoRetorno() {
		List<String> listaTitulo = context.select().from(Tables.POST).fetch().getValues(Tables.POST.ASSUNTO);
		List<String> listaIDsConvertendo = context.select().from(Tables.POST).fetch(Tables.POST.ID, String.class);
		String[] sobre = context.select().from(Tables.POST).fetchArray(Tables.POST.DESCRICAO);
	}

	/**
	 * Carregando uma interface.
	 */
	public static void usandoInterfaceProxyParaCarregamento() {
		context.selectFrom(Tables.POST).fetch().into(PostProxy.class).forEach(r -> {
			System.out.println(r);
		});
	}

	/**
	 * Aqui é importante o nome dos campos serem iguais
	 */
	public static void usandoPOJOParaCarregamento() {
		context.selectFrom(Tables.AUTHOR).fetch().into(AuthorDTO.class).forEach(r -> {
			System.out.println(r);
		});
	}

	/**
	 * Como usamos anotação, não importa o nome do campo.
	 */
	public static void usandoJPAAnnotatedPOJOParaCarregamento() {
		context.select().from(Tables.AUTHOR).fetch().into(MyAuthorJPAAnnotated.class).forEach(r -> {
			System.out.println(r);
		});
	}
}