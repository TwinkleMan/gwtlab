package com.twinkieman.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class GwtLab implements EntryPoint {

    private final GWTLabServiceAsync myService = GWTLabService.App.getInstance();

    final ListBox userListBox = new ListBox(false);
    final Label errorLabel = new Label();


    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // main elements
        final Button sendButton = new Button("Получить библиотеку игр");
        sendButton.addStyleName("sendButton");
        RootPanel.get("userListBoxContainer").add(userListBox);
        userListBox.addStyleName("userListBox");
        RootPanel.get("errorLabelContainer").add(errorLabel);
        RootPanel.get("sendButtonContainer").add(sendButton);

        //create user choice
        userListBox.setFocus(true);
        refreshUsers();

        //create main table
        final CellTable<GameLibraryUnit> mainTable = createCellTable();
        final ListDataProvider<GameLibraryUnit> mainDataProvider = new ListDataProvider<GameLibraryUnit>();
        mainDataProvider.addDataDisplay(mainTable);
        myService.getUserLibrary(userListBox.getName(), new AsyncCallback<List<GameLibraryUnit>>() {
            @Override
            public void onFailure(Throwable caught) {
                String errUser = userListBox.getName();
                errorLabel.setText("Ошибка сервера, невозможно получить библиотеку пользователя " + errUser+ "!");
            }

            @Override
            public void onSuccess(List<GameLibraryUnit> result) {
                mainDataProvider.setList(result);
            }
        });


        final DialogBox dialogBox = new DialogBox();
        dialogBox.setTitle("Библиотека игр пользователя");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Закрыть");
        closeButton.getElement().setId("closeButton");
        closeButton.addStyleName("closeButton");
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");

        final CellTable<GameLibraryUnit> userTable = createCellTable();
        dialogVPanel.add(userTable);

        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        class RPCClickHandler implements ClickHandler, KeyUpHandler {

            @Override
            public void onClick(ClickEvent event) {
                sendUsernameToServer();
            }

            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendUsernameToServer();
                }
            }

            private void sendUsernameToServer() {
                errorLabel.setText("");
                final String username = userListBox.getValue(userListBox.getSelectedIndex());

                sendButton.setEnabled(false);
                myService.getUserLibrary(username, new AsyncCallback<List<GameLibraryUnit>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Ошибка сервера!");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML("Ошибка сервера! Невозможно получить библиотеку игр пользователя.");
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    @Override
                    public void onSuccess(List<GameLibraryUnit> result) {
                        dialogBox.setText("Библиотека игр пользователя " + username);
                        dialogVPanel.setSpacing(20);
                        userTable.setRowCount(result.size(), true);
                        userTable.setRowData(0, result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }
        RPCClickHandler handler = new RPCClickHandler();
        sendButton.addClickHandler(handler);

    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }

    private void refreshUsers() {
        myService.getUserList(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                errorLabel.setText("Ошибка сервера, невозможно получить список пользователей!");
            }

            @Override
            public void onSuccess(List<String> result) {
                userListBox.clear();
                for (String res:result) userListBox.addItem(res);
            }
        });
    }

    private CellTable<GameLibraryUnit> createCellTable() {
        final CellTable<GameLibraryUnit> table = new CellTable<GameLibraryUnit>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<GameLibraryUnit> developerColumn = new TextColumn<GameLibraryUnit>() {
            @Override
            public String getValue(GameLibraryUnit object) {
                return object.getDeveloper();
            }
        };
        table.addColumn(developerColumn, "Разработчик");

        TextColumn<GameLibraryUnit> nameColumn = new TextColumn<GameLibraryUnit>() {
            @Override
            public String getValue(GameLibraryUnit object) {
                return object.getName();
            }
        };
        table.addColumn(nameColumn, "Название");

        TextColumn<GameLibraryUnit> isCompletedColumn = new TextColumn<GameLibraryUnit>() {
            @Override
            public String getValue(GameLibraryUnit object) {
                return object.isCompleted() ? "Да": "Нет";
            }
        };
        table.addColumn(isCompletedColumn, "Пройдена");

        return table;
    }
}
