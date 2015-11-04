package yporque;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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
public class ProductEditor extends VerticalLayout {

	private final ProductRepository repository;

	/**
	 * The currently edited product
	 */
	private Product product;

	/* Fields to edit properties in Customer entity */
	TextField code = new TextField("Código");
	TextField description = new TextField("Descripción");
	TextField stock = new TextField("Cantidad");
	TextField price = new TextField("Precio");

	/* Action buttons */
	Button save = new Button("Guardar", FontAwesome.SAVE);
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Borrar", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public ProductEditor(ProductRepository repository) {
		this.repository = repository;

		addComponents(code, stock, price, description, actions);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(product));
		delete.addClickListener(e -> repository.delete(product));
		cancel.addClickListener(e -> editProduct(product));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editProduct(Product c) {
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			product = repository.findOne(c.getId());
		}
		else {
			product = c;
		}
		cancel.setVisible(persisted);

		// Bind product properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		BeanFieldGroup.bindFieldsUnbuffered(product, this);

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
