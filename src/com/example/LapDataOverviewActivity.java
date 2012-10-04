
package com.example;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class LapDataOverviewActivity extends Activity {

	final Context context = this;
	String leftUp = "";
	String leftBottom = "";
	String rightBottom = "";
	int lastSelectedLeftUp = 0;
	int lastSelectedLeftBottom = 0;
	int lastSelectedRightBottom = 0;
	ArrayList<String> viewItems;
	Boolean right = true;
	TextView leftUpText;
	TextView leftBottomText;
	TextView rightBottomText;
	ImageView leftUpImage;
	ImageView leftBottomImage;
	ImageView rightBottomImage;
	LapData latestData;
	Spinner leftUpSpinner;
	Spinner leftBottomSpinner;
	Spinner rightBottomSpinner;
	SpeedSkatingApplication application;

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	application = (SpeedSkatingApplication) getApplication(); 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);
        leftUpText = (TextView) findViewById(R.id.leftUpText);
    	leftBottomText = (TextView) findViewById(R.id.leftBottomText);
    	rightBottomText = (TextView) findViewById(R.id.rightBottomText);
    	leftUpImage = (ImageView) findViewById(R.id.leftUpImage);
    	leftBottomImage = (ImageView) findViewById(R.id.leftBottomImage);
    	rightBottomImage = (ImageView) findViewById(R.id.rightBottomImage);
    	viewItems = new ArrayList<String>();
    	viewItems.add(getString(R.string.laneLabel));
    	viewItems.add(getString(R.string.totalTimeLabel));
    	viewItems.add(getString(R.string.roundTimeLabel));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.data_view, menu);
        SharedMenu.onCreateOptionsMenu(menu, this);
        
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change:
            	popupChange();
                return true;
            default:
                return SharedMenu.onOptionsItemSelected(item, this);
        }
    }

    public void onStart() {
    	super.onStart();
    	new DataHandlerWorker(handler, application.getSchedule());
    }
    
	private void popupChange() {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.view_popup);
		dialog.getWindow().setLayout(600,400);
		dialog.setTitle(getString(R.string.menu_change));
		leftUpSpinner = (Spinner) dialog.findViewById(R.id.leftUpSpinner);
		leftBottomSpinner = (Spinner) dialog.findViewById(R.id.leftBottomSpinner);
		rightBottomSpinner = (Spinner) dialog.findViewById(R.id.rightBottomSpinner);
		leftUpSpinner.setSelection(lastSelectedLeftUp);
		leftBottomSpinner.setSelection(lastSelectedLeftBottom);
		rightBottomSpinner.setSelection(lastSelectedRightBottom);		
		leftUpSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			String selected = "";
			@Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				selected = parentView.getItemAtPosition(position).toString();
			    if(!(selected.equals(leftUp))) {
			    	leftUp = selected;
			    }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        leftUp = parentView.getItemAtPosition(0).toString();
		    }
		});
		
		leftBottomSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    String selected = "";
			@Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	  selected = parentView.getItemAtPosition(position).toString();
		    	  if(!(selected.equals(leftBottom))) {
		    		  leftBottom = selected;
		    	  }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	leftBottom = parentView.getItemAtPosition(0).toString();
		    }
		});

		rightBottomSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			String selected = "";
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	selected = parentView.getItemAtPosition(position).toString();
		    	  if(!(selected.equals(rightBottom))) {
		    		  rightBottom = selected;
		    	  }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	rightBottom = parentView.getItemAtPosition(0).toString();
		    }
		});
		
		
		final Button saveButton = (Button) dialog.findViewById(R.id.changeViewButton);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				handleSelected();
			}
		});
		dialog.show();
	}
	
	 Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            // get the bundle and extract data by key
	            LapData data = (LapData) msg.obj;
	            latestData = data;
	            
	            TextView totalTime = (TextView) findViewById(R.id.totalTime);
	            TextView roundNumber = (TextView) findViewById(R.id.roundNumber);
	            TextView distance = (TextView) findViewById(R.id.distance);
	            totalTime.setText(data.getTotalTime());
	            roundNumber.setText(data.getRoundNumber());
	            distance.setText(data.getDistance());
	            refreshLeftUpScreen(data);
	            refreshLeftBottomScreen(data);
	            refreshRightBottomScreen(data);
	        }
	    };
	    
	    public void refreshLeftUpScreen(LapData data) {
	    	if(viewItems.size() > 0) {
	    		String value = viewItems.get(0);
	    		if(value.equals(getString(R.string.laneLabel))) {
	    				if(data.getLane().equals("l")) {
	    					leftUpImage.setImageResource(R.drawable.arrow_left);
	    				}else{
	    					leftUpImage.setImageResource(R.drawable.arrow);
	    				}
	    		} else {
	    			if(value.equals(getString(R.string.totalDifferenceLabel))) {
	    				if(data != null) {
	    					leftUpText.setText(data.getTotalDifference());
	    				}
	    			}else if(value.equals(getString(R.string.roundDifferenceLabel))) {
	    				if(data != null) {
	    					leftUpText.setText(data.getLapDifference());
	    				}
	    			}else if(value.equals(getString(R.string.totalTimeLabel))) {
	    				if(data != null) {
	    					leftUpText.setText(data.getTotalTime());
	    				}
	    			}else if(value.equals(getString(R.string.roundTimeLabel))) {
	    				if(data != null) {
	    					leftUpText.setText(data.getLapTime());
	    				}
	    			}else if(value.equals(getString(R.string.roundNumberLabel))) {
	    				if(data != null) {
	    					leftUpText.setText(data.getRoundNumber());
	    				}
	    			}else if(value.equals(getString(R.string.roundNumberLabel))) {
	    				if(data != null) {
	    					leftUpText.setText(data.getDistance());
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    public void refreshLeftBottomScreen(LapData data) {
	    if(viewItems.size() > 1) {	
	    		String value = viewItems.get(1);
	    		if(value.equals(getString(R.string.laneLabel))) {
	    			if(data.getLane().equals("l")) {
	    				leftBottomImage.setImageResource(R.drawable.arrow_left);
	    			}else{
	    				leftBottomImage.setImageResource(R.drawable.arrow);
	    			}
	    		} else {
	    			if(value.equals(getString(R.string.totalDifferenceLabel))) {
	    				if(data != null) {
	    					leftBottomText.setText(data.getTotalDifference());
	    				}
	    			}else if(value.equals(getString(R.string.roundDifferenceLabel))) {
	    				if(data != null) {
	    					leftBottomText.setText(data.getLapDifference());
	    				}
	    			}else if(value.equals(getString(R.string.totalTimeLabel))) {
	    				if(data != null) {
	    					leftBottomText.setText(data.getTotalTime());
	    				}
	    			}else if(value.equals(getString(R.string.roundTimeLabel))) {
	    				if(data != null) {
	    					leftBottomText.setText(data.getLapTime());
	    				}
	    			}else if(value.equals(getString(R.string.roundNumberLabel))) {
	    				if(data != null) {
	    					leftBottomText.setText(data.getRoundNumber());
	    				}
	    			}else if(value.equals(getString(R.string.distanceLabel))) {
	    				if(data != null) {
	    					leftBottomText.setText(data.getDistance());
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    public void refreshRightBottomScreen(LapData data) {
	    	if(viewItems.size() == 3) {
	    		String value = viewItems.get(2);
	    		if(value.equals(getString(R.string.laneLabel))) {
	    			if(data.getLane().equals("l")) {
	    				rightBottomImage.setImageResource(R.drawable.arrow_left);
	    			}else{
	    				rightBottomImage.setImageResource(R.drawable.arrow);
	    			}
	    		} else {
	    			if(value.equals(getString(R.string.totalDifferenceLabel))) {
	    				if(data != null) {
	    					rightBottomText.setText(data.getTotalDifference());
	    				}
	    			}else if(value.equals(getString(R.string.roundDifferenceLabel))) {
	    				if(data != null) {
	    					rightBottomText.setText(data.getLapDifference());
	    				}
	    			}else if(value.equals(getString(R.string.totalTimeLabel))) {
	    				if(data != null) {
	    					rightBottomText.setText(data.getTotalTime());
	    				}
	    			}else if(value.equals(getString(R.string.roundTimeLabel))) {
	    				if(data != null) {
	    					rightBottomText.setText(data.getLapTime());
	    				}
	    			}else if(value.equals(getString(R.string.roundNumberLabel))) {
	    				if(data != null) {
	    					rightBottomText.setText(data.getRoundNumber());
	    				}
	    			}else if(value.equals(getString(R.string.distanceLabel))) {
	    				if(data != null) {
	    					rightBottomText.setText(data.getDistance());
	    				}
	    			}
	    		}
	    	}
	    }

	  protected void handleSelected() {
		if(viewItems.size()  == 0) {			
			viewItems.add(leftUp);
			setLeftUpView();
		}else if(!(viewItems.get(0).equals(leftUp))) {			
			viewItems.set(0, leftUp);
			setLeftUpView();
		}
		if(viewItems.size()  < 2) {			
			viewItems.add(leftBottom);
			setLeftBottomView();
		}else if(viewItems.size() > 1 && !(viewItems.get(1).equals(leftBottom))) {
			viewItems.set(1, leftBottom);
			setLeftBottomView();
		}
		if(viewItems.size()  < 3) {
			viewItems.add(rightBottom);
			setRightBottomView();
		}else if(viewItems.size() == 3 && !(viewItems.get(2).equals(rightBottom))) {			
			viewItems.set(2, rightBottom);
			setRightBottomView();
		}
		refreshLeftUpScreen(latestData);
		refreshLeftBottomScreen(latestData);
		refreshRightBottomScreen(latestData);
		lastSelectedLeftUp = leftUpSpinner.getSelectedItemPosition();
		lastSelectedLeftBottom = leftBottomSpinner.getSelectedItemPosition();
		lastSelectedRightBottom = rightBottomSpinner.getSelectedItemPosition();
	}

	
	public void setLeftUpView() {
		if(leftUpImage.getVisibility() == View.VISIBLE) {
			if(!(viewItems.get(0).equals(getString(R.string.laneLabel)))) {
				leftUpImage.setVisibility(View.GONE);
				leftUpText.setVisibility(View.VISIBLE);
			}				
		} else {
			if(viewItems.get(0).equals(getString(R.string.laneLabel))) {
				leftUpImage.setVisibility(View.VISIBLE);
				leftUpText.setVisibility(View.GONE);
			}
		}
	}
	
	public void setLeftBottomView() {
		if(leftBottomImage.getVisibility() == View.VISIBLE) {
			if(!(viewItems.get(1).equals(getString(R.string.laneLabel)))) {
				leftBottomImage.setVisibility(View.GONE);
				leftBottomText.setVisibility(View.VISIBLE);
			}
		} else {
			if(viewItems.get(1).equals(getString(R.string.laneLabel))) {
				leftBottomImage.setVisibility(View.VISIBLE);
				leftBottomText.setVisibility(View.GONE);
			}
		}
	}
	
	public void setRightBottomView() {
		if(rightBottomImage.getVisibility() == View.VISIBLE) {
			if(!(viewItems.get(2).equals(getString(R.string.laneLabel)))) {
				rightBottomImage.setVisibility(View.GONE);
				rightBottomText.setVisibility(View.VISIBLE);
			}
		} else {
			if(viewItems.get(2).equals(getString(R.string.laneLabel))) {
				rightBottomImage.setVisibility(View.VISIBLE);
				rightBottomText.setVisibility(View.GONE);		
			}
		}
	}
}