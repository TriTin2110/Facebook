/**
 * 
 */
const node = document.getElementById("search");

node.addEventListener("keypress", (event) =>
{
	if(event.key === 'Enter')
	{
		document.getElementById("searching-form").submit()
	}
})