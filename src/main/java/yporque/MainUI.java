
package yporque;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@SpringUI
@Theme("valo")
public class MainUI extends UI {

	private final ProductRepository repo;

	private final ProductEditor editor;

	private final Grid grid;

	private final TextField filter;

	private final Button addNewBtn;

	@Autowired
	public MainUI(ProductRepository repo, ProductEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid();
		this.filter = new TextField();
		this.addNewBtn = new Button("Nuevo Producto", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		HorizontalLayout secondaryLayout = new HorizontalLayout(grid, editor);
		secondaryLayout.setSpacing(true);
		VerticalLayout mainLayout = new VerticalLayout(actions, secondaryLayout);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(500, Unit.PIXELS);
		grid.setColumns("id", "description", "code", "stock", "price");

		filter.setInputPrompt("filtrar por código");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listProducts(e.getText()));
		filter.setWidth(300,Unit.PIXELS);

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editor.setVisible(false);
			}
			else {
				editor.editProduct((Product) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editProduct(new Product("", "",0,0.0)));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listProducts(filter.getValue());
		});

		// Initialize listing
		listProducts(null);
	}

	// tag::listProducts[]
	private void listProducts(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setContainerDataSource(
					new BeanItemContainer(Product.class, repo.findAll()));
		}
		else {
			grid.setContainerDataSource(new BeanItemContainer(Product.class,
					repo.findByCodeStartsWithIgnoreCase(text)));
		}
	}
	// end::listProducts[]

}
