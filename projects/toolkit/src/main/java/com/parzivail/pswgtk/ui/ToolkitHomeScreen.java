package com.parzivail.pswgtk.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.parzivail.pswg.Resources;
import com.parzivail.pswgtk.screen.JComponentScreen;
import com.parzivail.pswgtk.swing.EventHelper;
import com.parzivail.pswgtk.swing.NodeTreeModel;
import com.parzivail.pswgtk.util.LangUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class ToolkitHomeScreen extends JComponentScreen
{
	//region
	{
		// GUI initializer generated by IntelliJ IDEA GUI Designer
		// >>> IMPORTANT!! <<<
		// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$()
	{
		root = new JPanel();
		root.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
		root.setOpaque(true);
		final JSplitPane splitPane1 = new JSplitPane();
		root.add(splitPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		splitPane1.setLeftComponent(scrollPane1);
		availableTools = new JTree();
		availableTools.setRootVisible(true);
		availableTools.setShowsRootHandles(false);
		scrollPane1.setViewportView(availableTools);
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
		splitPane1.setRightComponent(panel1);
		lToolTitle = new JLabel();
		lToolTitle.setText("Tool Title");
		panel1.add(lToolTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer1 = new Spacer();
		panel1.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel1.add(panel2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		bRunTool = new JButton();
		bRunTool.setText("Run");
		panel2.add(bRunTool, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer2 = new Spacer();
		panel2.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
		tbToolDesc = new JTextArea();
		tbToolDesc.setEditable(false);
		tbToolDesc.setEnabled(true);
		tbToolDesc.setFocusable(false);
		tbToolDesc.setLineWrap(true);
		tbToolDesc.setOpaque(false);
		tbToolDesc.setRequestFocusEnabled(false);
		tbToolDesc.setWrapStyleWord(true);
		panel1.add(tbToolDesc, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$()
	{
		return root;
	}

	public static class Tool
	{
		private final String id;
		private final Function<Screen, Screen> screenProvider;

		public Tool(String id, Function<Screen, Screen> screenProvider)
		{
			this.id = id;
			this.screenProvider = screenProvider;
		}

		public String getTitle()
		{
			return LangUtil.translate("tool.title." + id);
		}

		public String getDescription()
		{
			return LangUtil.translate("tool.description." + id);
		}
	}

	private static NodeTreeModel.Node<Tool> createToolNode(String id, Function<Screen, Screen> screenProvider)
	{
		return new NodeTreeModel.Node<Tool>(LangUtil.translate("tool.title." + id), new Tool(id, screenProvider));
	}

	public static final String I18N_TOOLKIT_HOME = Resources.screen("toolkit_home");

	private JPanel root;
	private JTree availableTools;
	private JLabel lToolTitle;
	private JButton bRunTool;
	private JTextArea tbToolDesc;

	public ToolkitHomeScreen(Screen parent)
	{
		super(parent, Text.translatable(I18N_TOOLKIT_HOME));

		lToolTitle.putClientProperty("FlatLaf.styleClass", "h1");

		availableTools.setModel(new NodeTreeModel<>(
				new NodeTreeModel.Node<Tool>(LangUtil.translate("tool.category.tools"), null)
						.child(new NodeTreeModel.Node<Tool>(LangUtil.translate("tool.category.modeling"), null)
								       .child(createToolNode("nemi_compiler", null))
								       .child(createToolNode("p3di_compiler", null))
						)
						.child(new NodeTreeModel.Node<Tool>(LangUtil.translate("tool.category.worldgen"), null)
								       .child(createToolNode("worldgen_visualizer", ToolkitWorldgenScreen::new))
						))
		);

		availableTools.addTreeSelectionListener(e -> {
			NodeTreeModel.Node<Tool> tool = getSelectedTool();

			if (tool.value == null)
			{
				lToolTitle.setText("");
				tbToolDesc.setText("");
			}
			else
			{
				lToolTitle.setText(tool.value.getTitle());
				tbToolDesc.setText(tool.value.getDescription());
			}

			bRunTool.setVisible(tool.value != null && tool.value.screenProvider != null);
		});

		availableTools.addSelectionRow(0);

		EventHelper.click(bRunTool, mouseEvent -> {
			assert this.client != null;

			var tool = getSelectedTool();
			if (tool.value != null && tool.value.screenProvider != null)
			{
				this.client.execute(() -> {
					this.client.setScreen(tool.value.screenProvider.apply(this));
				});
			}
		});
	}

	@SuppressWarnings("unchecked")
	private NodeTreeModel.Node<Tool> getSelectedTool()
	{
		return (NodeTreeModel.Node<Tool>)availableTools.getLastSelectedPathComponent();
	}

	@Override
	protected JComponent getRootComponent()
	{
		return root;
	}

	@Override
	protected void renderContent(MatrixStack matrices)
	{

	}
}
