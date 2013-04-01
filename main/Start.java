package main;

import ihm.PGridTetris;
import ihm.PNextBlock;
import ihm.PPartie;
import ihm.TetrisFrame;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;

import sound.MidiPlayList;
import sound.MidiPlayer;
import action.ActionManager;
import action.ActionReceiver;
import action.Actions;

public class Start extends JApplet {

	private static final long serialVersionUID = 1L;

	private static PGridTetris pTetris;

	private static Tetris cTetris;

	private static PNextBlock nextBlockPanel;

	private static PPartie statePanel;

	private static ActionReceiver actionReceiver;

	private static ActionManager actionManager;

	private static Timer rigther, lefter, downer;
        private static Timer test;
        
        
	public static void main(String[] args) {
		startIn(new TetrisFrame());
	}

	public void init() {
		setSize(2 * (PGridTetris.squareWidth * Tetris.width) + 2,
				PGridTetris.squareHeight * (Tetris.height + PNextBlock.height)
						+ 2);
		startIn(this);
	}

	private static void startIn(Container c) {

		MidiPlayList playbacks = new MidiPlayList("Tetris.jar", ".pb");

		MidiPlayer player = new MidiPlayer();
		player.setPlayList(playbacks);

		actionReceiver = new ActionReceiver();
		actionManager = new ActionManager(actionReceiver);

		statePanel = new PPartie(player, playbacks);
		cTetris = new Tetris(statePanel, actionReceiver);
		statePanel.setTetris(cTetris);
		new Actions(cTetris);
		nextBlockPanel = new PNextBlock(cTetris);
		pTetris = new PGridTetris(cTetris);

		cTetris.setGridPanel(pTetris);
		cTetris.setNextBlockPanel(nextBlockPanel);

		c.setLayout(null);

		nextBlockPanel.setLocation(0, 0);

		pTetris.setBounds(0, PGridTetris.squareHeight * PNextBlock.height,
				PGridTetris.squareWidth * Tetris.width + 2,
				PGridTetris.squareHeight * Tetris.height + 2);

		statePanel.setBounds(PGridTetris.squareWidth * Tetris.width + 2, 0,
				PGridTetris.squareWidth * Tetris.width,
				PGridTetris.squareHeight * (Tetris.height + PNextBlock.height)
						+ 2);

		c.add(pTetris);
		c.add(nextBlockPanel);
		c.add(statePanel);

		c.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (lefter == null)
						lefter = new Timer(actionReceiver, cTetris
								.getTimeBetweenActions(), Actions
								.getActionLeft(), cTetris
								.getDelayBeforeRepetition());
					if (!lefter.isRunning())
						lefter.myStart();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (rigther == null)
						rigther = new Timer(actionReceiver, cTetris
								.getTimeBetweenActions(), Actions
								.getActionRight(), cTetris
								.getDelayBeforeRepetition());

					if (!rigther.isRunning())
						rigther.myStart();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (downer == null)
						downer = new Timer(actionReceiver, cTetris
								.getTimeBetweenActions() / 10, Actions
								.getActionDown(), cTetris
								.getDelayBeforeRepetition());

					if (!downer.isRunning())
						downer.myStart();
				}
				if (e.getKeyCode() == KeyEvent.VK_UP)
					actionReceiver.addAction(Actions.getActionRotate(), false);
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
					actionReceiver.addAction(Actions.getActionPause(), true);
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					actionReceiver.addAction(Actions.getActionStop(), true);
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					lefter.finish();
					lefter = null;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rigther.finish();
					rigther = null;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					downer.finish();
					downer = null;
				}
			}

			public void keyTyped(KeyEvent e) {
			}

		});

		cTetris.start();
		actionManager.start();

		c.setVisible(true);

	}

	public void stop() {

	}

}
