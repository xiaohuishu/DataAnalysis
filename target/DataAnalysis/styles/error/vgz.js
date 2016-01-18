$(document).ready(function(){

	/* Change current menu on scroll
	var sectionProfile = $("#profile").offset().top-180;
	var sectionProjets = $("#projets").offset().top-180;
	var sectionContact = $("#contact").offset().top-180;

	$(document).scroll(function(){
	    if($(this).scrollTop() > sectionProfile){   
	        $('.current').removeClass('current');
			$('a[href=#profile]').addClass('current');
	    }
	    if($(this).scrollTop() > sectionProjets){   
	        $('.current').removeClass('current');
			$('a[href=#projets]').addClass('current');
	    }
	    if($(this).scrollTop() > sectionContact){   
	        $('.current').removeClass('current');
			$('a[href=#contact]').addClass('current');
	    }
	});*/

	/* Smoothscroll */
	/* by creativejuiz */
	$('.menu a[href^="#"]').click(function(){
		$('.current').removeClass('current');
		$(this).addClass('current');
		var header_height = $('.header').height()+50;
	    var the_id = $(this).attr("href");
	    var scrollPos = $(the_id).offset().top-header_height;
	    $('html, body').animate({
	        scrollTop:scrollPos
	    }, 'slow');

	    return false;
	}); 
	/* Scroll to top on logo click */
	$('.heading').on('click', function(){
		$('.current').removeClass('current');
		$('.menu li:first a').addClass('current');
		$('html, body').animate({
	        scrollTop:0
	    }, 'slow');
	});



	/* Projects slider - using bx-slider */
	$('.projectsSlider').bxSlider({
		auto: true,
		pause: 5000,
	  	onSliderLoad: function(){
	    	/* Position nav. and bullets */
			var topPosition = $('.projectsSlider img').height();
			$('.bx-prev, .bx-next').css('top',topPosition/2);
			$('.projets .bx-pager').css('top',topPosition-30);
	  	}
	});	
    /* Show & hide slider nav.*/
    if( $('.projectsSlider li').length > 1 ){
	    $(".sliderwrapper").hover(function(){
		    $('.bx-prev, .bx-next').fadeIn(200);
		},function(){
		    $('.bx-prev, .bx-next').delay(3000).fadeOut(200);
		});

		if( $('.touch') ){
		    $('.bx-prev, .bx-next').show();
		}
	}



	/* Fancybox for widescreen */
	$(".fancybox").fancybox({
		padding: 0,
		titleShow: 	false,
		autoDimensions: false
	});



	/* Envoi du formulaire */
	$('.contact-form').submit(function(){
		var form = $(this);
		var nom = $("input[name=name]").val();
		var mail = $("input[name=email]").val();
		var message = $("textarea[name=message]").val();
		var error = false;
		var busy = null;

		// Pour tous les champs avec l'attribut required
		form.find('.required').each(function(){
			// On vérifie si le champ est vide
			if($.trim( $(this).val() ) == ''){
				$(this).addClass('inputerror');
				error = true;
			}
			else {
				$(this).removeClass('inputerror');
			}
		});

		if(!error){
			if(busy)
				busy.abort();

			busy = $.ajax({
				url: ajaxurl, // a créer !!
				type: 'POST',
				data: form.serialize(),
				success: function(response){

					if(response == 'success'){
						// On vide le formulaire
						form[0].reset();

						// On affiche un message de succès
						$('.noty').attr('class','noty success').html('Votre message a bien été envoyé.');
					}

					if( response == 'error' ){
						// On affiche un message d'erreur
						$('.noty').attr('class','noty error').html("Une erreur est survenue lors de l'envoi de votre message.");

					}
				}
			});
		} else {
			// On affiche un message d'erreur
			$('.noty').attr('class','noty error').html("Merci de compléter tous les champs.");
		}

		// On affiche le message … qui se masque au bout de 5s.
		$('.noty').fadeIn();
		$('.noty').delay('5000').fadeOut();
	});


});