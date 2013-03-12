package org.jyu.itks545.group1.oauth.twitter;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * A small Java Swing GUI for oauth authorization demonstration usin PIN-based
 * method
 *
 * @author Bela Borbely <bela.z.borbely at gmail.com>
 * @version 14.2.2013
 */
public class DemoOAuthTester extends javax.swing.JFrame {

    private DemoOAuth.RequestToken requestToken;
    private DemoOAuth.Authorize authorizer;
    private DemoOAuth.AccessToken accessToken;
    private DemoOAuth.AuthorizedRequest authorizedRequest;

    public DemoOAuthTester() {
        initComponents();
        jButtonGetRequestToken.setAction(getRequestTokenAction());
        jButtonGetAccessToken.setAction(getAccessTokenAction());
        jButtonAddMessage.setAction(getAddMessageAction());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelRequestToken = new javax.swing.JPanel();
        jButtonGetRequestToken = new javax.swing.JButton();
        jLabelOauthToken = new javax.swing.JLabel();
        jPanelAuthorize = new javax.swing.JPanel();
        jLabelAddPIN = new javax.swing.JLabel();
        jTextFieldAddPIN = new javax.swing.JTextField();
        jButtonGetAccessToken = new javax.swing.JButton();
        jPanelAccessToken = new javax.swing.JPanel();
        jLabelAccessToken = new javax.swing.JLabel();
        jPanelAuthorizedRequest = new javax.swing.JPanel();
        jTextFieldAddMessage = new javax.swing.JTextField();
        jButtonAddMessage = new javax.swing.JButton();
        jLabelAuthoreizedRequestResult = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Desktop Twitter OAuth Tester");
        setMinimumSize(new java.awt.Dimension(800, 250));
        getContentPane().setLayout(new java.awt.GridLayout(4, 1));

        jPanelRequestToken.setBorder(javax.swing.BorderFactory.createTitledBorder("request_token"));
        jPanelRequestToken.setMinimumSize(new java.awt.Dimension(550, 80));
        jPanelRequestToken.setPreferredSize(new java.awt.Dimension(550, 80));
        jPanelRequestToken.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jButtonGetRequestToken.setText("Get request_token and authorization");
        jPanelRequestToken.add(jButtonGetRequestToken);

        jLabelOauthToken.setText("oauth_token:");
        jPanelRequestToken.add(jLabelOauthToken);

        getContentPane().add(jPanelRequestToken);

        jPanelAuthorize.setBorder(javax.swing.BorderFactory.createTitledBorder("authorize"));
        jPanelAuthorize.setMinimumSize(new java.awt.Dimension(550, 80));
        jPanelAuthorize.setPreferredSize(new java.awt.Dimension(550, 80));
        jPanelAuthorize.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabelAddPIN.setText("Add PIN");
        jPanelAuthorize.add(jLabelAddPIN);

        jTextFieldAddPIN.setPreferredSize(new java.awt.Dimension(80, 20));
        jPanelAuthorize.add(jTextFieldAddPIN);

        jButtonGetAccessToken.setText("Get access_token");
        jPanelAuthorize.add(jButtonGetAccessToken);

        getContentPane().add(jPanelAuthorize);

        jPanelAccessToken.setBorder(javax.swing.BorderFactory.createTitledBorder("access_token"));
        jPanelAccessToken.setMinimumSize(new java.awt.Dimension(550, 80));
        jPanelAccessToken.setPreferredSize(new java.awt.Dimension(550, 80));
        jPanelAccessToken.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabelAccessToken.setText("access_token:");
        jPanelAccessToken.add(jLabelAccessToken);

        getContentPane().add(jPanelAccessToken);

        jPanelAuthorizedRequest.setBorder(javax.swing.BorderFactory.createTitledBorder("authorized request"));
        jPanelAuthorizedRequest.setMinimumSize(new java.awt.Dimension(550, 80));
        jPanelAuthorizedRequest.setPreferredSize(new java.awt.Dimension(550, 80));
        jPanelAuthorizedRequest.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jTextFieldAddMessage.setPreferredSize(new java.awt.Dimension(80, 20));
        jPanelAuthorizedRequest.add(jTextFieldAddMessage);

        jButtonAddMessage.setText("Add message");
        jPanelAuthorizedRequest.add(jButtonAddMessage);
        jPanelAuthorizedRequest.add(jLabelAuthoreizedRequestResult);

        getContentPane().add(jPanelAuthorizedRequest);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DemoOAuthTester().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton jButtonAddMessage;
    protected javax.swing.JButton jButtonGetAccessToken;
    protected javax.swing.JButton jButtonGetRequestToken;
    protected javax.swing.JLabel jLabelAccessToken;
    protected javax.swing.JLabel jLabelAddPIN;
    protected javax.swing.JLabel jLabelAuthoreizedRequestResult;
    protected javax.swing.JLabel jLabelOauthToken;
    protected javax.swing.JPanel jPanelAccessToken;
    protected javax.swing.JPanel jPanelAuthorize;
    protected javax.swing.JPanel jPanelAuthorizedRequest;
    protected javax.swing.JPanel jPanelRequestToken;
    protected javax.swing.JTextField jTextFieldAddMessage;
    protected javax.swing.JTextField jTextFieldAddPIN;
    // End of variables declaration//GEN-END:variables

    /**
     * GUI Action for getting request_token
     *
     * @return
     */
    private Action getRequestTokenAction() {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestToken = new DemoOAuth.RequestToken();
                try {
                    requestToken.sendRequest();
                    jLabelOauthToken.setText("oauth_token: " + requestToken.response_oauth_token());
                    jLabelOauthToken.setToolTipText("oauth_token: " + requestToken.response_oauth_token());
                    jTextFieldAddPIN.grabFocus();
                    authorizer = new DemoOAuth.Authorize(requestToken);
                    Desktop.getDesktop().browse(authorizer.getURI());
                } catch (Exception ex) {
                    Logger.getLogger(DemoOAuthTester.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        action.putValue(Action.NAME, jButtonGetRequestToken.getText());
        action.putValue(Action.SHORT_DESCRIPTION, jButtonGetRequestToken.getText());
        action.putValue(Action.LONG_DESCRIPTION, jButtonGetRequestToken.getText());
        return action;
    }

    /**
     * GUI Action for getting request_token
     *
     * @return
     */
    private Action getAccessTokenAction() {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pin = jTextFieldAddPIN.getText();
                    accessToken = new DemoOAuth.AccessToken(pin, requestToken);
                    
                    accessToken.sendRequest();
                    jLabelAccessToken.setText("access_token: " + accessToken.response_oauth_token());
                    jLabelAccessToken.setToolTipText("access_token: " + accessToken.response_oauth_token());
                } catch (Exception ex) {
                    Logger.getLogger(DemoOAuthTester.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        action.putValue(Action.NAME, jButtonGetAccessToken.getText());
        action.putValue(Action.SHORT_DESCRIPTION, jButtonGetAccessToken.getText());
        action.putValue(Action.LONG_DESCRIPTION, jButtonGetAccessToken.getText());
        return action;
    }
    
    /**
     * GUI Action for getting request_token
     *
     * @return
     */
    private Action getAddMessageAction() {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String message = jTextFieldAddMessage.getText();
                    authorizedRequest = new DemoOAuth.AuthorizedRequest(message, accessToken);
                    
                    authorizedRequest.sendRequest();
                    jLabelAuthoreizedRequestResult.setText("Result: " + authorizedRequest.getResponseString());
                    jLabelAuthoreizedRequestResult.setToolTipText("Result: " + authorizedRequest.getResponseString());
                } catch (Exception ex) {
                    Logger.getLogger(DemoOAuthTester.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        action.putValue(Action.NAME, jButtonAddMessage.getText());
        action.putValue(Action.SHORT_DESCRIPTION, jButtonAddMessage.getText());
        action.putValue(Action.LONG_DESCRIPTION, jButtonAddMessage.getText());
        return action;
    }
    
    
}
