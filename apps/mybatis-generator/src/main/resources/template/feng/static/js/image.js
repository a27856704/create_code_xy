//本js依赖jquery
//	www.zeroplace.cn
//	高灰
//	2011/08/09
function ResizeImage(obj,color){
	// img=new Image();
	if(obj.length>1){
		alert('select error');
		return ;
	}

	if(color==undefined){
		color='#ffffff';
	}

	var width=obj.attr('oldwidth');
	var height=obj.attr('oldheight');

	if(width==undefined){
		width=obj.attr('width');
		height=obj.attr('height');
	}

	var pos=obj.attr('pos');

	if(pos==undefined){
		pos=0;
	}

	var src=obj.attr('src');
	var img=new Image();
	img.src=src;
	var oldwidth=img.width;
	var oldheight=img.height;

	var pw=width/oldwidth;
	var ph=height/oldheight;
	var left=0;
	var top=0;

	if(pw<ph){
		img.width=width;
		img.height=oldheight*pw;
		top=parseInt((height-img.height)/2);
	}else{
		img.height=height;
		img.width=oldwidth*ph;
		left=parseInt((width-img.width)/2);
	}
	obj.attr('width',img.width);
	obj.attr('height',img.height);
	obj.attr('oldwidth',width);
	obj.attr('oldheight',height);
	obj.attr('src',img.src);
	obj.css('position','absolute');
	obj.css('left',left);
	if(pos==1)
	{
		obj.css('bottom',0);
	}
	if(pos==0){
		obj.css('top',top);
	}
	if(pos==-1){
		obj.css('top',0)
	}

	//包裹
	if(obj.parent("div[autosize='yes']").length==0){
		var div=document.createElement('div');
		$(div).css('width',width);
		$(div).css('height',height);
		$(div).css('position','relative');
		$(div).css('border','none');
		$(div).attr('autosize','yes');
		$(div).css('background-color',color);
		obj.wrap(div);
	}
	delete img;
}

jQuery.fn.extend({
	autosize:function(color){
		this.each(function(){
			ResizeImage($(this),color);
		})
	}
});

$(function(){
	$("img[autosize='yes']").autosize('#999');
});