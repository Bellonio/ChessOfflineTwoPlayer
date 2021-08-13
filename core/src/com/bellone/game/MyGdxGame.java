package com.bellone.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import static com.bellone.game.Casella.ORIGINAL_W_H_PEDINA;
import static com.bellone.game.Casella.WIDTH_PED;
import static com.bellone.game.Casella.casellaBiancaColor;
import static com.bellone.game.Casella.casellaNeraColor;

public class MyGdxGame extends ApplicationAdapter {

	public final String imgFrecciaTurno = "frecciaTurno.png";
	public final String imgFrecciaVittoria = "frecciaVittoria.png";
	public final String imgStallo = "stallo.png";
	public final String imgStallo2 = "stalloOpposti.png";
	public final String imgVittoriaBianchi = "vittoriaBianchi.png";
	public final String imgSconfittaBianchi = "sconfittaBianchi.png";
	public final String imgVittoriaNeri = "vittoriaNeri.png";
	public final String imgSconfittaNeri = "sconfittaNeri.png";
	public final String imgImpostazioni = "settings.png";

	private final int ORIGINAL_WIDTH_FRECCE = 215;
	private final int ORIGINAL_HEIGHT_FRECCE = 360;

	private Texture t_frecciaTurno;
	private Texture t_frecciaVittoria;
	private Texture t_stallo;
	private Texture t_stallo2;
	private Texture t_vittoriaBianchi;
	private Texture t_sconfittaBianchi;
	private Texture t_vittoriaNeri;
	private Texture t_sconfittaNeri;
	private Texture t_impost;
	private Texture t_woodBackgorund;

	private int WIDTH_FRECCE;
	private int HEIGHT_FRECCE;
	private int X_FRECCIA_SX_TURNO1;
	private int X_FRECCIA_C_TURNO1;
	private int X_FRECCIA_DX_TURNO1;
	private int Y_FRECCIA_TURNO1;

	private int X_FRECCIA_SX_TURNO2;
	private int X_FRECCIA_C_TURNO2;
	private int X_FRECCIA_DX_TURNO2;
	private int Y_FRECCIA_TURNO2;

	private boolean msgFinePart;
	private int WIDTH_FINE_PART;
	private int HEIGHT_FINE_PART;

	private Rectangle rectImpostazioni;
	private int WIDTH_IMPOSTAZIONI;
	private int X_IMPOSTAZIONI;
	private int Y_IMPOSTAZIONI;


	//----- scelta nuova pedina ------
	private int WIDTH_SCELTA_PEDINA;
	private int HEIGHT_SCELTA_PEDINA;
	private int X_SCELTA_PEDINA;
	private int Y_SCELTA_PEDINA;

	private Rectangle rectTorre;
	private Rectangle rectCavallo;
	private Rectangle rectAlfiere;
	private Rectangle rectRegina;
	//_____ scelta nuova pedina ______

	private ShapeRenderer sr;
	private SpriteBatch batch;

	private Scacchiera scacchiera;
	public Impostazioni impostazioniView;
	public boolean mostraImpostazioni;
	public static Preferences prefs;
	public static boolean pezziOpposti;
	public static boolean mosseVisibili;

	@Override
	public void create () {
		sr = new ShapeRenderer();
		OrthographicCamera cam = new OrthographicCamera();
		batch = new SpriteBatch();

		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		t_woodBackgorund = new Texture("wood.png");
		t_frecciaTurno = new Texture(imgFrecciaTurno);
		t_frecciaVittoria = new Texture(imgFrecciaVittoria);
		t_stallo = new Texture(imgStallo);
		t_stallo2 = new Texture(imgStallo2);
		t_vittoriaBianchi = new Texture(imgVittoriaBianchi);
		t_sconfittaBianchi = new Texture(imgSconfittaBianchi);
		t_vittoriaNeri = new Texture(imgVittoriaNeri);
		t_sconfittaNeri = new Texture(imgSconfittaNeri);

		impostazioniView = new Impostazioni(batch);
		mostraImpostazioni = false;
		t_impost = new Texture(imgImpostazioni);
		prefs = Gdx.app.getPreferences("Settings");
		readSettings();

		scacchiera = new Scacchiera(sr, batch);

		//Sul mio tel (width = 1080): 700x275
		// w = 1080/1.54;  h = w/2.54
		WIDTH_SCELTA_PEDINA = (int) (Gdx.graphics.getWidth()/1.545454);
		HEIGHT_SCELTA_PEDINA = (int) (WIDTH_SCELTA_PEDINA/2.545454);
		X_SCELTA_PEDINA = Gdx.graphics.getWidth()/2 - WIDTH_SCELTA_PEDINA /2;
		Y_SCELTA_PEDINA = Gdx.graphics.getHeight()/2 - HEIGHT_SCELTA_PEDINA /2;
		//Sul mio tel (width = 1080): 25
		int w_CAS_SCELTA_PEDINA = WIDTH_PED * 2 - Gdx.graphics.getWidth()/43;

		//Sul mio tel (width = 1080): 70
		int y_caselle = (int) (Y_SCELTA_PEDINA+(Gdx.graphics.getWidth()/15.43)),
				//Sul mio tel (width = 1080): 33
				x_casella = (int) (X_SCELTA_PEDINA+(Gdx.graphics.getWidth()/32.73));
		rectTorre = new Rectangle(x_casella, y_caselle,
				w_CAS_SCELTA_PEDINA, w_CAS_SCELTA_PEDINA);

		//Sul mio tel (width = 1080): 33
		x_casella += w_CAS_SCELTA_PEDINA +(Gdx.graphics.getWidth()/32.73);
		rectCavallo = new Rectangle(x_casella, y_caselle,
				w_CAS_SCELTA_PEDINA, w_CAS_SCELTA_PEDINA);

		//Sul mio tel (width = 1080): 36
		x_casella += w_CAS_SCELTA_PEDINA +(Gdx.graphics.getWidth()/30);
		rectAlfiere = new Rectangle(x_casella, y_caselle,
				w_CAS_SCELTA_PEDINA, w_CAS_SCELTA_PEDINA);

		//Sul mio tel (width = 1080): 33
		x_casella += w_CAS_SCELTA_PEDINA +(Gdx.graphics.getWidth()/32.73);
		rectRegina = new Rectangle(x_casella, y_caselle,
				w_CAS_SCELTA_PEDINA, w_CAS_SCELTA_PEDINA);


		//Sul mio tel (width = 1080): 180
		WIDTH_IMPOSTAZIONI = (int) (Gdx.graphics.getWidth()/6.5);
		//Sul mio tel (width = 1080): 20
		X_IMPOSTAZIONI = Gdx.graphics.getWidth() - WIDTH_IMPOSTAZIONI - (Gdx.graphics.getWidth()/54);
		Y_IMPOSTAZIONI = Gdx.graphics.getHeight() - WIDTH_IMPOSTAZIONI - (Gdx.graphics.getWidth()/54);
		rectImpostazioni = new Rectangle(X_IMPOSTAZIONI, Y_IMPOSTAZIONI,
				WIDTH_IMPOSTAZIONI,WIDTH_IMPOSTAZIONI);


		//Sul mio tel (width = 1080): 140x250
		WIDTH_FRECCE = (int) (Gdx.graphics.getWidth()/7.2);
		HEIGHT_FRECCE = (int) (WIDTH_FRECCE*1.66);
		//Sul mio tel (width = 1080): 40
		X_FRECCIA_SX_TURNO1 = Gdx.graphics.getWidth()/27;
		X_FRECCIA_C_TURNO1 = Gdx.graphics.getWidth()/2 - WIDTH_FRECCE/2;
		X_FRECCIA_DX_TURNO1 = Gdx.graphics.getWidth() - WIDTH_FRECCE - Gdx.graphics.getWidth()/27;
		//Sul mio tel (width = 1080): 30
		Y_FRECCIA_TURNO1 = Gdx.graphics.getWidth()/36;

		//Sul mio tel (width = 1080): 40
		X_FRECCIA_SX_TURNO2 = Gdx.graphics.getWidth()/27;
		X_FRECCIA_C_TURNO2 = (X_IMPOSTAZIONI/2) - WIDTH_FRECCE/2;
		//Sul mio tel (width = 1080): 40
		X_FRECCIA_DX_TURNO2 = X_IMPOSTAZIONI - WIDTH_FRECCE - Gdx.graphics.getWidth()/27;
		//Sul mio tel (width = 1080): 30
		Y_FRECCIA_TURNO2 = Gdx.graphics.getHeight() - HEIGHT_FRECCE - Gdx.graphics.getWidth()/36;

		msgFinePart = true;
		//Sul mio tel (width = 1080): 200
		WIDTH_FINE_PART = (int) (Gdx.graphics.getWidth() - (Gdx.graphics.getWidth()/5.4));
		HEIGHT_FINE_PART = (int) (WIDTH_FINE_PART/5.6);

		/* Perche non uso semplicemeente nel render() if(Gdx.input.justTouched())
          e prendo le coord. con Gdx.input.getX() e Gdx.input.getY() ???

        Perchè con questo modo l'utente può diciamo annullare il fatto di aver
         cliccato rilasciando in un punto vuoto */
		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) { return false; }
			@Override
			public boolean keyUp(int keycode) { return false; }
			@Override
			public boolean keyTyped(char character) { return false; }
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				float mouseX = Gdx.input.getX();
				//Il click ha coordinate che partono (cioè sono uguali a 0;0)
				// nell'angolo in ALTO A SX (invece per tutto il resto l'origine
				// è IN BASSO A SX)
				float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

				if(!mostraImpostazioni) {
					if(rectImpostazioni.contains(mouseX, mouseY)){
						mostraImpostazioni = true;
					}else if((scacchiera.bianchiVincono || scacchiera.neriVincono
						|| scacchiera.stallo)
							&& scacchiera.clickScacchiera(mouseX, mouseY)){
						msgFinePart = false;
					}

					if (!scacchiera.mostraSceltaNuovaPedina) {
						scacchiera.clickCasella(mouseX, mouseY);
					} else {
						if (rectTorre.contains(mouseX, mouseY)) {
							scacchiera.scelto_nuova_pedina(1);
						} else if (rectCavallo.contains(mouseX, mouseY)) {
							scacchiera.scelto_nuova_pedina(2);
						} else if (rectAlfiere.contains(mouseX, mouseY)) {
							scacchiera.scelto_nuova_pedina(3);
						} else if (rectRegina.contains(mouseX, mouseY)) {
							scacchiera.scelto_nuova_pedina(4);
						}
					}
				}else{
					if(impostazioniView.clickPezziUguali(mouseX, mouseY)){
						setPezziOpposti(false);
					}else if(impostazioniView.clickPezziOpposti(mouseX, mouseY)){
						setPezziOpposti(true);
					}else if(impostazioniView.clickMosseVisibili(mouseX, mouseY)){
						changeMosseVisibiliState();
					}else if(impostazioniView.clickBack(mouseX, mouseY)){
						mostraImpostazioni = false;
					}
				}

				return false;
			}
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
			@Override
			public boolean mouseMoved(int screenX, int screenY) { return false; }
			@Override
			public boolean scrolled(float amountX, float amountY) { return false; }
		});
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(t_woodBackgorund, 0, 0);
		batch.end();

		if(!mostraImpostazioni) {
			scacchiera.disegna();

			batch.begin();
			batch.draw(t_impost, X_IMPOSTAZIONI, Y_IMPOSTAZIONI, WIDTH_IMPOSTAZIONI, WIDTH_IMPOSTAZIONI);
			batch.end();

			//Sul mio tel (width = 1080): 10
			float dim = (float) Gdx.graphics.getWidth()/108;
			if (scacchiera.mostraSceltaNuovaPedina) {
				batch.begin();
				batch.draw(Scacchiera.textureSceltaNuovaPedina, X_SCELTA_PEDINA, Y_SCELTA_PEDINA,
						WIDTH_SCELTA_PEDINA, HEIGHT_SCELTA_PEDINA);
				batch.end();


				float width_pedina = rectTorre.width - dim;
				int[] bg_color;
				//(ho gia cambiato il flag)
				if (!scacchiera.turno_bianchi) {
					bg_color = casellaNeraColor;
					sr.begin(ShapeRenderer.ShapeType.Filled);
					sr.setColor(bg_color[0] / 255f, bg_color[1] / 255f, bg_color[2] / 255f, 1);

					sr.rect(rectTorre.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.rect(rectCavallo.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.rect(rectAlfiere.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.rect(rectRegina.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.end();


					batch.begin();
					batch.draw(Scacchiera.b_tTorre, rectTorre.x + dim/2, rectTorre.y + dim/2,
							width_pedina, width_pedina);
					batch.end();

					batch.begin();
					batch.draw(Scacchiera.b_tCavallo, rectCavallo.x + dim/2, rectCavallo.y + dim/2,
							width_pedina, width_pedina);
					batch.end();

					batch.begin();
					batch.draw(Scacchiera.b_tAlfiere, rectAlfiere.x + dim/2, rectAlfiere.y + dim/2,
							width_pedina, width_pedina);
					batch.end();

					batch.begin();
					batch.draw(Scacchiera.b_tRegina, rectRegina.x + dim/2, rectRegina.y + dim/2,
							width_pedina, width_pedina);
					batch.end();
				} else {
					bg_color = casellaBiancaColor;
					sr.begin(ShapeRenderer.ShapeType.Filled);
					sr.setColor(bg_color[0] / 255f, bg_color[1] / 255f, bg_color[2] / 255f, 1);

					sr.rect(rectTorre.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.rect(rectCavallo.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.rect(rectAlfiere.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.rect(rectRegina.x, rectTorre.y, rectTorre.width, rectTorre.height);
					sr.end();

					batch.begin();
					batch.draw(Scacchiera.n_tTorre, rectTorre.x + dim/2, rectTorre.y + dim/2,
							width_pedina, width_pedina, 0, 0, ORIGINAL_W_H_PEDINA, ORIGINAL_W_H_PEDINA,
							false, pezziOpposti);
					batch.end();

					batch.begin();
					batch.draw(Scacchiera.n_tCavallo, rectCavallo.x + dim/2, rectCavallo.y + dim/2,
							width_pedina, width_pedina, 0, 0, ORIGINAL_W_H_PEDINA, ORIGINAL_W_H_PEDINA,
							false, pezziOpposti);
					batch.end();

					batch.begin();
					batch.draw(Scacchiera.n_tAlfiere, rectAlfiere.x + dim/2, rectAlfiere.y + dim/2,
							width_pedina, width_pedina, 0, 0, ORIGINAL_W_H_PEDINA, ORIGINAL_W_H_PEDINA,
							false, pezziOpposti);
					batch.end();

					batch.begin();
					batch.draw(Scacchiera.n_tRegina, rectRegina.x + dim/2, rectRegina.y + dim/2,
							width_pedina, width_pedina, 0, 0, ORIGINAL_W_H_PEDINA, ORIGINAL_W_H_PEDINA,
							false, pezziOpposti);
					batch.end();
				}

			}

			if(!scacchiera.stallo) {
				if (scacchiera.bianchiVincono) {
					turno_dei_bianchi(true);
					if(msgFinePart){
						batch.begin();
						batch.draw(t_vittoriaBianchi,
								Gdx.graphics.getWidth()/2 - WIDTH_FINE_PART/2,
								Gdx.graphics.getHeight()/2 - HEIGHT_FINE_PART - dim/2,
								WIDTH_FINE_PART, HEIGHT_FINE_PART);
						batch.end();

						batch.begin();
						batch.draw(pezziOpposti ? t_sconfittaNeri : t_sconfittaBianchi,
								Gdx.graphics.getWidth()/2 - WIDTH_FINE_PART/2,
								Gdx.graphics.getHeight()/2 + dim/2,
								WIDTH_FINE_PART, HEIGHT_FINE_PART);
						batch.end();
					}
				} else if (scacchiera.neriVincono) {
					turno_dei_neri(true);
					if(msgFinePart){
						batch.begin();
						batch.draw(t_sconfittaBianchi,
								Gdx.graphics.getWidth()/2 - WIDTH_FINE_PART/2,
								Gdx.graphics.getHeight()/2 - HEIGHT_FINE_PART - dim/2,
								WIDTH_FINE_PART, HEIGHT_FINE_PART);
						batch.end();

						batch.begin();
						batch.draw(pezziOpposti ? t_vittoriaNeri : t_vittoriaBianchi,
								Gdx.graphics.getWidth()/2 - WIDTH_FINE_PART/2,
								Gdx.graphics.getHeight()/2 + dim/2,
								WIDTH_FINE_PART, HEIGHT_FINE_PART);
						batch.end();
					}
				} else {
					if (scacchiera.turno_bianchi) {
						turno_dei_bianchi(false);
					} else {
						turno_dei_neri(false);
					}
				}
			}else{
				if(msgFinePart) {
					batch.begin();
					batch.draw(pezziOpposti ? t_stallo2 : t_stallo,
							Gdx.graphics.getWidth() / 2 - WIDTH_FINE_PART / 2,
							Gdx.graphics.getHeight() / 2 -
									(!pezziOpposti ? HEIGHT_FINE_PART / 2 : HEIGHT_FINE_PART),
							WIDTH_FINE_PART,
							!pezziOpposti ? HEIGHT_FINE_PART : HEIGHT_FINE_PART * 2);
					batch.end();
				}
			}
		}else{
			impostazioniView.disegna();
		}
	}

	public void readSettings(){
		pezziOpposti = prefs.getBoolean("pezziOpposti", true);
		mosseVisibili = prefs.getBoolean("mosseVisibili", true);
	}
	public void setPezziOpposti(boolean value){
		pezziOpposti = value;
		prefs.putBoolean("pezziOpposti", value);
		prefs.flush();
	}
	public void changeMosseVisibiliState(){
		mosseVisibili = !mosseVisibili;
		prefs.putBoolean("mosseVisibili", mosseVisibili);
		prefs.flush();
	}

	private void turno_dei_bianchi(boolean vinto){
		batch.begin();
		batch.draw(vinto ? t_frecciaVittoria : t_frecciaTurno,
				X_FRECCIA_SX_TURNO1, Y_FRECCIA_TURNO1,
				WIDTH_FRECCE, HEIGHT_FRECCE, 0,0,
				ORIGINAL_WIDTH_FRECCE, ORIGINAL_HEIGHT_FRECCE, false, !pezziOpposti);
		batch.end();
		batch.begin();
		batch.draw(vinto ? t_frecciaVittoria : t_frecciaTurno,
				X_FRECCIA_C_TURNO1, Y_FRECCIA_TURNO1,
				WIDTH_FRECCE, HEIGHT_FRECCE, 0,0,
				ORIGINAL_WIDTH_FRECCE, ORIGINAL_HEIGHT_FRECCE, false, !pezziOpposti);
		batch.end();
		batch.begin();
		batch.draw(vinto ? t_frecciaVittoria : t_frecciaTurno,
				X_FRECCIA_DX_TURNO1, Y_FRECCIA_TURNO1,
				WIDTH_FRECCE, HEIGHT_FRECCE, 0,0,
				ORIGINAL_WIDTH_FRECCE, ORIGINAL_HEIGHT_FRECCE, false, !pezziOpposti);
		batch.end();
	}
	private void turno_dei_neri(boolean vinto){
		batch.begin();
		batch.draw(vinto ? t_frecciaVittoria : t_frecciaTurno,
				X_FRECCIA_SX_TURNO2, Y_FRECCIA_TURNO2,
				WIDTH_FRECCE, HEIGHT_FRECCE, 0,0,
				ORIGINAL_WIDTH_FRECCE, ORIGINAL_HEIGHT_FRECCE, false, pezziOpposti);
		batch.end();
		batch.begin();
		batch.draw(vinto ? t_frecciaVittoria : t_frecciaTurno,
				X_FRECCIA_C_TURNO2, Y_FRECCIA_TURNO2,
				WIDTH_FRECCE, HEIGHT_FRECCE, 0,0,
				ORIGINAL_WIDTH_FRECCE, ORIGINAL_HEIGHT_FRECCE, false, pezziOpposti);
		batch.end();
		batch.begin();
		batch.draw(vinto ? t_frecciaVittoria : t_frecciaTurno,
				X_FRECCIA_DX_TURNO2, Y_FRECCIA_TURNO2,
				WIDTH_FRECCE, HEIGHT_FRECCE, 0,0,
				ORIGINAL_WIDTH_FRECCE, ORIGINAL_HEIGHT_FRECCE, false, pezziOpposti);
		batch.end();
	}

	@Override
	public void dispose () {
		sr.dispose();
		batch.dispose();
	}
}
