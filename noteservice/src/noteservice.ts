import { Request, Response } from 'express';

/**
 * The Note service, that handles CRUD operations.
 */
export class Noteservice {
    constructor() {

    }

    getNote(req: Request, res: Response) {
		const id = parseInt(req.params.id, 10);
		
		res.status(200).send({
			success: 'true',
			message: 'todos retrieved successfully',
			todos: {'id': id}
		});
    }
}