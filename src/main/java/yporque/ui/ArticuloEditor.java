package yporque.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import yporque.model.Articulo;
import yporque.model.Product;
import yporque.repository.ArticuloRepository;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form in
 * multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class ArticuloEditor extends VerticalLayout {

	private final ArticuloRepository articuloRepository;

	/**
	 * The currently edited articulo
	 */
	private Articulo articulo;

	/* Fields to edit properties in Customer entity */
	TextField code = new TextField("Código");
	TextArea description = new TextArea("Descripción");
	TextField stock = new TextField("Cantidad");
	TextField price = new TextField("Precio");

	/* Action buttons */
	Button save = new Button("Guardar", FontAwesome.SAVE);
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Borrar", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public ArticuloEditor(ArticuloRepository repository) {
		this.articuloRepository = repository;

		addComponents(code, stock, price, description, actions);
		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(articulo));
		delete.addClickListener(e -> repository.delete(articulo));
		cancel.addClickListener(e -> edit(articulo));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void edit(Articulo c) {
		final boolean persisted = c.getArticuloId() != 0;
		if (persisted) {
			// Find fresh entity for editing
			articulo = articuloRepository.findOne(c.getArticuloId());
		}
		else {
			articulo = c;
		}
		cancel.setVisible(persisted);

		// Bind articulo properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		BeanFieldGroup.bindFieldsUnbuffered(articulo, this);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		code.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
