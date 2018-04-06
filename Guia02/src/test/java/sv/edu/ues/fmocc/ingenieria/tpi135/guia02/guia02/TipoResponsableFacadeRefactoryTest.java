package sv.edu.ues.fmocc.ingenieria.tpi135.guia02.guia02;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.powermock.reflect.Whitebox;
import sv.edu.ues.fmocc.ingenieria.tpi135.mantenimiento.mantenimientolib.entity.TipoResponsable;

/**
 *
 * @author zaldivar
 */
public class TipoResponsableFacadeRefactoryTest {

    @ClassRule//Le puse esto por que con @Rule est objeto no puede ser statico
    public static EntityManagerProvider emp;

    @BeforeClass
    public static void init() {
        emp = EntityManagerProvider.getInstance("mantenimientoTestPU");

    }

    @Test
    public void when_creating_null_tipo_responsable_expect_false() {
        TipoResponsableFacade trf = insertForTest();

        boolean result = trf.crear(null);

        assertEquals(0, trf.findAll().size());
        assertFalse(result);
    }

    @Test
    public void when_creating_new_tipo_responsable_expect_true() {
        TipoResponsable nuevoTipoResponsable = new TipoResponsable();
        nuevoTipoResponsable.setNombreTipoResponsable("Test Tipo Responsable");
        nuevoTipoResponsable.setDetalleTipoResponsable("Algun detalle de prueba");
        nuevoTipoResponsable.setActivo(true);

        TipoResponsableFacade trf = insertForTest();

        boolean result = trf.crear(nuevoTipoResponsable);

        assertTrue(result);
        assertEquals(1, trf.findAll().size());
    }

    @Test
    public void when_modify_valid_tipo_responsable_expect_true() {
        TipoResponsableFacade trf = insertForTest();

        trf.getEntityManager().persist(new TipoResponsable(null, "test tipo responsable", true));

        TipoResponsable expected = new TipoResponsable(1, "changed tipo responsable", false);
        TipoResponsable modified = new TipoResponsable(1, "changed tipo responsable", false);

        TipoResponsable result = trf.edit(expected);

        assertNotNull(result.getIdTipoResponsable());
        assertEquals(result.getNombreTipoResponsable(), expected.getNombreTipoResponsable());
    }

    @Test
    public void when_delete_null_tipo_responsable_expect_false() {
        TipoResponsableFacade trf = insertForTest();

        boolean result = trf.eliminar(null);
        assertEquals(0, trf.findAll().size());
        assertFalse(result);
    }

    @Test
    public void when_delete_valid_tipo_responsable_expect_true() {
        TipoResponsableFacade trf = insertForTest();

        trf.getEntityManager().persist(new TipoResponsable(null, "test tipo responsable", true));
        TipoResponsable entity = new TipoResponsable(1);
        boolean result = trf.eliminar(entity);
        assertTrue(result);
    }

    @Test
    public void findAll() {
        TipoResponsableFacade trf = insertForTest();

        trf.getEntityManager().persist(new TipoResponsable(1, "test tipo responsable", true));
        trf.getEntityManager().persist(new TipoResponsable(2, "test tipo responsable", true));
        List<TipoResponsable> list = trf.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void find() {
        TipoResponsableFacade trf = insertForTest();
        trf.getEntityManager().persist(new TipoResponsable(1, "test find", true));
        TipoResponsable result = trf.find(1);
        assertEquals("test find", result.getNombreTipoResponsable());
    }

    public TipoResponsableFacade insertForTest() {
        TipoResponsableFacade trf = new TipoResponsableFacade();
        Whitebox.setInternalState(trf, "em", emp.getEm());
        trf.getEntityManager().getTransaction().begin();
        return trf;
    }

    @After
    public void cleanup() {
        emp.getTx().rollback();
    }

    @AfterClass
    public static void tearDown() {
        emp.killInstance();
    }

}
